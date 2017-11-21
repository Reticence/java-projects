package com.meridian.nodules;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.meridian.nodules.model.NoNoduleData;
import com.meridian.nodules.model.NoduleData;
import com.meridian.nodules.model.ThyroidFunctionData;
import com.meridian.nodules.model.ThyroidNodulesData;
import com.meridian.nodules.model.ThyroidScreeningData;
import com.meridian.utils.PoiUtil;

/**
 * 
 * @author Reticence (liuyang_blue@qq.com)
 * @date 2017年3月29日 下午2:26:38
 * @version 1.0
 * @parameter
 */
public class FileHandler {
    private final String[] assessment = { "未知结果", "良性", "极低危", "低危", "中危", "高危", };
    private String[] title = { "体检编号", "体检日期", "甲功结果", "结节编号", "评估结果", "Tirads结果", "建议", "Json字符串", };  // 
    private String[][] functionInfo = {
            { "T4", "T3", "FT3", "FT4", "TSH", "A_TPO", "A_TG", "TG", }, // name
            { "71.2~141", "1.49~2.60", "4.26~8.10", "10.0~28.2", "0.465~4.68", "0~34", "0~1.75", "0~115", },  // range
            { "nmol/ml", "nmol/L", "pmol/dL", "pmol/ML", "uIU/ml", "IU/ml", "IU/ml", "IU/ml", },  // unit
    };
    private Workbook workbook;
    private Sheet sheet;
    private String writePath;
    private int rownum;

    private String peId;
    
    public String[] getAssessment() {
        return assessment;
    }
    
    public void initWriteFileProcessing(String writePath) {
        this.writePath = writePath;
        workbook = new SXSSFWorkbook();
        sheet = workbook.createSheet();
        rownum = 0;

        Row row = sheet.createRow(rownum++);
        for (int i = 0; i < title.length; i++) {
            PoiUtil.setCellValue(row, i, title[i]);
        }
    }
    
    public void writeFileProcessing(String[] peInfo, ThyroidScreeningData tsd) {
        if (tsd.isThyroidDataIntegrityFlag()) {
            Row row = sheet.createRow(rownum++);
            ThyroidNodulesData tnd = tsd.getThyroidNodulesData();
            if (tnd.getNodulesCount() > 0) {
                for (int i = 0; i < tnd.getNodulesCount(); i++) {
                    NoduleData noduleData = tnd.getNoduleDataList().get(i);
                    if (i > 0) {
                        row = sheet.createRow(rownum++);
                    }
                    PoiUtil.setCellValue(row, 0, peInfo[0]);
                    if (peInfo.length > 1) {
                        PoiUtil.setCellValue(row, 1, peInfo[1]);
                    }
                    PoiUtil.setCellValue(row, 2, tsd.getEvaluateResultData().getThyroidFunctionResult());
                    PoiUtil.setCellValue(row, 3, (i + 1) + "");
                    PoiUtil.setCellValue(row, 4, assessment[Integer.parseInt(noduleData.getNoduleEstimateResult())]);
                    PoiUtil.setCellValue(row, 5, noduleData.getNoduleTiradsResult());
                    PoiUtil.setCellValue(row, 6, noduleData.getNoduleSuggest());
                    PoiUtil.setCellValue(row, 7, JSON.toJSONString(noduleData));
                }
            } else {
                NoNoduleData noNoduleData = tnd.getNoNoduleData();
                PoiUtil.setCellValue(row, 0, peInfo[0]);
                if (peInfo.length > 1) {
                    PoiUtil.setCellValue(row, 1, peInfo[1]);
                }
                PoiUtil.setCellValue(row, 2, tsd.getEvaluateResultData().getThyroidFunctionResult());
                PoiUtil.setCellValue(row, 3, "0");
                PoiUtil.setCellValue(row, 4, "无结节");
                PoiUtil.setCellValue(row, 5, "无结节");
                PoiUtil.setCellValue(row, 6, noNoduleData.getThyroidBodySuggest());
                PoiUtil.setCellValue(row, 7, JSON.toJSONString(noNoduleData));
            }
        }
    }
    
    public void closeWriteFileProcessing() {
        try {
            FileOutputStream outputStream = new FileOutputStream(writePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Map<String, ThyroidScreeningData> readFileProcessing(String path, int sheetNum) {
        Map<String, ThyroidScreeningData> results = new HashMap<String, ThyroidScreeningData>();
        try {
            Workbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(sheetNum);
            Row row;
            
            int lastRowNum = sheet.getLastRowNum();
            for (int rownum = 0; rownum <= lastRowNum; rownum++) {
                row = sheet.getRow(rownum);
                String peId = PoiUtil.getValue(row.getCell(6));
                this.peId = peId;
                String peDate = PoiUtil.getValue(row.getCell(5));
                if (null == row || StringUtils.isBlank(peId)) {
                    continue;
                }
                if (rownum == 0) {
                    // TODO
                    continue;
                }
                String ultrasonography = PoiUtil.getValue(row.getCell(7));
                ThyroidScreeningData tsd = new ThyroidScreeningData();
                ThyroidNodulesData tnd = analysisThyroidNodulesData(ultrasonography);
                tsd.setThyroidNodulesData(tnd);
                List<ThyroidFunctionData> thyroidFunctionData = new ArrayList<ThyroidFunctionData>();
                for (int i = 0; i < 8; i++) {
                    String indexName = functionInfo[0][i];
                    String referenceRange = functionInfo[1][i];
                    String indexUnit = functionInfo[2][i];
                    String indexValue = PoiUtil.getValue(row.getCell(i + 10));
                    int normalFlag = 0;
                    String[] range = null;
                    
                    if (null == indexValue) {
                        continue;
                    }
                    if (!"".equals(referenceRange) && referenceRange.contains("~")) {
                        range = referenceRange.split("~");
                    } else {
                        range = new String[] { "0", "9999999999", };
                    }

                    try {
                        if (Float.parseFloat(indexValue) < Float.parseFloat(range[0])) {
                            normalFlag = -1;
                        } else if (Float.parseFloat(indexValue) > Float.parseFloat(range[1])) {
                            normalFlag = 1;
                        }
                    } catch (NumberFormatException e) {
                        if ("".equals(indexValue)) {
                            normalFlag = 0;
                        } else if ("".equals(referenceRange) || referenceRange == null) {
                            normalFlag = 0;
                        } else if (referenceRange.equals(indexValue)) {
                            normalFlag = 0;
                        } else {
                            normalFlag = 1;
                        }
                    } catch (NullPointerException e) {
                        normalFlag = 0;
                    }
                    
                    ThyroidFunctionData tfd = new ThyroidFunctionData();
                    tfd.setIndexName(indexName);
                    tfd.setIndexRange(referenceRange);
                    tfd.setIndexUnit(indexUnit);
                    tfd.setIndexValue(indexValue);
                    tfd.setIndexNormalFlag(normalFlag);
                    thyroidFunctionData.add(tfd);
                }
                tsd.setThyroidFunctionData(thyroidFunctionData);
                results.put(peId + "_" + peDate, tsd);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Map<String, ThyroidScreeningData> readFileProcessing(Workbook workbook, int sheetNum, int peIdColNum, Integer peDateColNum, int ultrasonographyColNum) {
        Map<String, ThyroidScreeningData> results = new HashMap<String, ThyroidScreeningData>();
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Row row;
        
        int lastRowNum = sheet.getLastRowNum();
        for (int rownum = 0; rownum <= lastRowNum; rownum++) {
            row = sheet.getRow(rownum);
            if (null == row) {
                continue;
            }
            String peId = PoiUtil.getValue(row.getCell(peIdColNum));
            String peDate = "";
            if (null != peDateColNum) {
                peDate = PoiUtil.getValue(row.getCell(peDateColNum));
            }
            this.peId = peId;
            if (null == row || StringUtils.isBlank(peId)) {
                continue;
            }
            if (rownum == 0) {
                // TODO
                continue;
            }
            String ultrasonography = PoiUtil.getValue(row.getCell(ultrasonographyColNum));
            ThyroidScreeningData tsd = new ThyroidScreeningData();
            ThyroidNodulesData tnd = analysisThyroidNodulesData(ultrasonography);
            tsd.setThyroidNodulesData(tnd);
            results.put(peId + "_" + peDate, tsd);
        }
        return results;
    }
    
    public ThyroidNodulesData analysisThyroidNodulesData(String data) {
        String[] dataArr = data.replaceAll("[\\t\\n\\r ]", "").trim().split("。");
        ThyroidNodulesData tnd;
        try {
            tnd = getUltraData(dataArr);
        } catch (Exception e) {
            tnd = new ThyroidNodulesData();
            System.out.println("Calss=FileHandler; Method=analysisThyroidNodulesData;");
            System.out.println("data={" + data + "}");
            e.printStackTrace();
        }
        return tnd;
    }
    
    private ThyroidNodulesData getUltraData(String[] dataArr){
        int arrLength = dataArr.length;
        int nodules = 0;
        ThyroidNodulesData tnd = new ThyroidNodulesData();
        NoNoduleData noNoduleData;
        List<NoduleData> noduleDataList;
        
        if(arrLength > 0){
            //计算结节个数
            for(int i=0;i<arrLength;i++){
                if(dataArr[i].toString().contains("结节大小")){
                    nodules +=1;
                }
            }
            tnd.setNodulesCount(nodules);
            noduleDataList = new ArrayList<NoduleData>();
            tnd.setNoduleDataList(noduleDataList);
            
            //循环解析超声结节数据
            for(int i=0;i<arrLength;i++){
                String[] onArr =null;
                String valStr ="";
                
                //获取节点的各种信息
                if(dataArr[i].toString().contains("结节大小")){
                    NoduleData noduleData = new NoduleData();
                    noduleDataList.add(noduleData);
                    
                    if(i==0){
                        onArr = dataArr[i].toString().split("多发结节");
                        if(onArr.length == 1){
                            onArr = dataArr[i].toString().split("单发结节");
                        }
                        valStr = onArr[onArr.length-1].toString();
                    } else{
                        valStr = dataArr[i].toString();
                    }
                    
                    //判断是否符合模板
                    
                    //左右叶
                    /*if(valStr.contains("左叶")){
                        noduleData.setNoduleSite("左叶");
                    } else if(valStr.contains("右叶")){
                        noduleData.setNoduleSite("右叶");
                    } else {
                        if(fileFlag==1 && valStr.contains("峡部")){
                            noduleData.setNoduleSite("峡部");
                        } else {
                            noduleData.setNoduleSite("");
                        }
                    }*/
                    
                    //部位
                    if(valStr.contains("上部")){
                        noduleData.setNoduleSite("上部");
                    } else if(valStr.contains("上中部")){
                        noduleData.setNoduleSite("上中部");
                    } else if(valStr.contains("中部")){
                        noduleData.setNoduleSite("中部");
                    } else if(valStr.contains("中下部")){
                        noduleData.setNoduleSite("中下部");
                    } else if(valStr.contains("下部")){
                        noduleData.setNoduleSite("下部");
                    } else{
                        if(valStr.contains("峡部")){
                            noduleData.setNoduleSite("峡部");
                        } else {
                            noduleData.setNoduleSite("");
                        }
                    }
                    
                    //结节大小
                    int xIndex = valStr.indexOf("×");
                    int jIndex = valStr.indexOf("结节大小")+4;
                    int cIndex = valStr.indexOf("m", xIndex);
                    
                    if(xIndex > 0 ){
                        String sizeStr= valStr.substring(jIndex,cIndex+1).replace("为", "").replace(",", "");
                        String[] tmpArr = sizeStr.split("×");
                        float size01 = 0;
                        try {
                            size01 = Float.valueOf(tmpArr[0].trim().replace("cm", ""));
                        } catch (NumberFormatException e1) {
                            System.out.println(peId + " >>> " + sizeStr);
                            e1.printStackTrace();
                        }
                        float size02;
                        try {
                            size02 = Float.valueOf(tmpArr[1].trim().replace("cm", ""));
                            if(size02>size01){
                                float tmpSize = size01;
                                size01 = size02;
                                size02 = tmpSize;
                            }
                        } catch (NumberFormatException e) {
                            size02 = size01;
                        }
//                          System.out.println("size01: "+size01);
//                          System.out.println("size02: "+size02);
                        noduleData.setNoduleSize(String.valueOf(size01));
                    } else{
                        noduleData.setNoduleSize("");
                    }
                    
                    //结构及二级结构
                    if(valStr.contains("囊实性")){
                        noduleData.setNoduleStructure("囊实性");
                        if(valStr.contains("海绵征")){
                            noduleData.setNoduleSecondStructure("海绵征");
                        } else if(valStr.contains("实性部分不偏心")){
                            noduleData.setNoduleSecondStructure("实性部分不偏心");
                        } else{
                            noduleData.setNoduleSecondStructure("实性部分偏心");
                        }
                    } else if(valStr.contains("囊性")){
                        noduleData.setNoduleStructure("囊性");
                        noduleData.setNoduleSecondStructure("");
                    } else if(valStr.contains("实性")){
                        noduleData.setNoduleStructure("实性");
                        noduleData.setNoduleSecondStructure("");
                    } else{
                        noduleData.setNoduleStructure("");
                        noduleData.setNoduleSecondStructure("");
                    }
                    
                    //回声
                    if(valStr.contains("高回声")){
                        noduleData.setNoduleHyperecho("高回声");
                    } else if(valStr.contains("等回声")){
                        noduleData.setNoduleHyperecho("等回声");
                    } else if(valStr.contains("低回声")){
                        noduleData.setNoduleHyperecho("低回声");
                    } else if(valStr.contains("极低回声")){
                        noduleData.setNoduleHyperecho("极低回声");
                    } else if(valStr.contains("无回声")){
                        noduleData.setNoduleHyperecho("无回声");
                    } else{
                        noduleData.setNoduleHyperecho("");
                    }
                    
                    //边缘
                    if(valStr.contains("边缘规则")){
                        noduleData.setNoduleEdge("边缘规则");
                    } else if(valStr.contains("边缘不规则")){
                        noduleData.setNoduleEdge("边缘不规则");
                    } else{
                        noduleData.setNoduleEdge("");
                    }
                    
                    //纵横比
                    if(valStr.contains("纵横比")){
                        int zIndex = valStr.indexOf("纵横比")+2;
                        noduleData.setNoduleRatio(valStr.substring(zIndex+1,zIndex+3));
                    } else{
                        noduleData.setNoduleRatio("");
                    }
                    
                    //钙化
                    if(valStr.contains("无钙化")){
                        noduleData.setNoduleCalcification("无钙化");
                    } else if(valStr.contains("微钙化")){
                        noduleData.setNoduleCalcification("微钙化");
                    } else if(valStr.contains("不完整环状钙化且钙化外周有低回声成分")){
                        noduleData.setNoduleCalcification("不完整环状钙化且钙化外周有低回声成分");
                    } else if(valStr.contains("其他钙化")){
                        noduleData.setNoduleCalcification("其他钙化");
                    } else{
                        noduleData.setNoduleCalcification("");
                    }
                    
                    //腺体外侵犯
                    if(valStr.contains("无腺体外侵犯")){
                        noduleData.setNoduleInvasion("无腺体外侵犯");
                    } else if(valStr.contains("有腺体外侵犯")){
                        noduleData.setNoduleInvasion("有腺体外侵犯");
                    } else{
                        noduleData.setNoduleInvasion("");
                    }
                    
                    //血流情况
                    if(valStr.contains("周边和内部无血流信号")){
                        noduleData.setNoduleBloodstream("周边和内部无血流信号");
                    } else if(valStr.contains("少许内部血流且无周边环状血流")){
                        noduleData.setNoduleBloodstream("少许内部血流且无周边环状血流");
                    } else if(valStr.contains("周边环状血流及少许内部血流")){
                        noduleData.setNoduleBloodstream("周边环状血流及少许内部血流");
                    } else if(valStr.contains("周边环状血流及中等内部血流")){
                        noduleData.setNoduleBloodstream("周边环状血流及中等内部血流");
                    } else if(valStr.contains("丰富内部血流伴周边环状血流")){
                        noduleData.setNoduleBloodstream("丰富内部血流伴周边环状血流");
                    } else if(valStr.contains("丰富内部血流不伴周边环状血流")){
                        noduleData.setNoduleBloodstream("丰富内部血流不伴周边环状血流");
                    } else if(valStr.contains("内部血流丰富伴周边环状血流")){
                        noduleData.setNoduleBloodstream("内部血流丰富伴周边环状血流");
                    } else if(valStr.contains("内部血流丰富不伴周边环状血流")){
                        noduleData.setNoduleBloodstream("内部血流丰富不伴周边环状血流");
                    } else if(valStr.contains("周边环状血流")){
                        noduleData.setNoduleBloodstream("周边环状血流");
                    }else{
                        noduleData.setNoduleBloodstream("");
                    }                           
                }
            }
            
        } 
        
        //无结节时候
        if(nodules == 0){
            noNoduleData = new NoNoduleData();
            tnd.setNoNoduleData(noNoduleData);
            
            for(int i=dataArr.length-1; i>=0; i--){
                String thyDistri = dataArr[dataArr.length-1];
//                System.out.println("thyDistri: "+thyDistri);
                String hyper = "";
                String distri = "";
                //回声
                if(thyDistri.contains("回声中等")){
                    hyper = "中等";
                } else if(thyDistri.contains("回声减低")){
                    hyper = "减低";
                } else if(thyDistri.contains("回声增高")){
                    hyper = "增高";
                }
                
                //分布
                if(thyDistri.contains("不均匀分布")){
                    distri = "不均匀";
                } else if(thyDistri.contains("均匀分布")){
                    distri = "均匀";
                } else if(thyDistri.contains("片状分布")){
                    distri = "片状";
                }
                
                if(!"".equals(hyper) || !"".equals(distri)){
                    noNoduleData.setThyroidBodyEcho(hyper);
                    noNoduleData.setThyroidBodyDistribution(distri);
                    break;
                }
            }
        }
        return tnd;
    }
}
