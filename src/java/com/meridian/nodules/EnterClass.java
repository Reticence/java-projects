package com.meridian.nodules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.meridian.nodules.model.NoNoduleData;
import com.meridian.nodules.model.NoduleData;
import com.meridian.nodules.model.ThyroidNodulesData;
import com.meridian.nodules.model.ThyroidScreeningData;

/**
 * 
 * @author 刘洋
 * @date 2017年3月29日 下午2:06:24
 * @version 1.0
 * @parameter
 */
public class EnterClass {
    public static final String ASSESSMENT_RESULT = "assessmentResult";
    public static final String TIRADS_RESULT = "tiradsResult";
    public static final String SUGGEST_RESULT = "suggestResult";
    
    /**
     * 
     * @author Reticence (liuyang_blue@qq.com)
     * @param path : 文件路径
     * @param peIdColNum : 体检编号所在列号
     * @param peDateColNum : 体检日期所在列号
     * @param ultrasonographyColNum : 超声描述所在列号
     */
    public static void batchAssessmentEnter(String path, int peIdColNum, Integer peDateColNum, int ultrasonographyColNum) {
        batchAssessmentEnter(path, -1, peIdColNum, peDateColNum, ultrasonographyColNum);
    }
    
    /**
     * 
     * @author Reticence (liuyang_blue@qq.com)
     * @param path : 文件路径
     * @param sheetNum : sheetNum
     * @param peIdColNum : 体检编号所在列号
     * @param peDateColNum : 体检日期所在列号
     * @param ultrasonographyColNum : 超声描述所在列号
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void batchAssessmentEnter(String path, int sheetNum, int peIdColNum, Integer peDateColNum, int ultrasonographyColNum) {
        String writePath = path.substring(0, path.length() - 5) + "_assessment.xlsx";
        FileHandler fileHandler = new FileHandler();
        fileHandler.initWriteFileProcessing(writePath);
//        Map<String, ThyroidScreeningData> results = fileHandler.readFileProcessing(path, sheetNum);

        Map<String, ThyroidScreeningData> results = null;
        try {
            Workbook workbook = new XSSFWorkbook(path);
            if (sheetNum < 0) {
                results = new HashMap<String, ThyroidScreeningData>();
                int sheets = workbook.getNumberOfSheets();
                for (int i = 0; i < sheets; i++) {
                    results.putAll(fileHandler.readFileProcessing(workbook, i, peIdColNum, peDateColNum, ultrasonographyColNum));
                }
            } else {
                results = fileHandler.readFileProcessing(workbook, sheetNum, peIdColNum, peDateColNum, ultrasonographyColNum);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        List<String> resultsKeys = new ArrayList<String>(results.keySet());
        try {
            Collections.sort(resultsKeys, new Comparator() {
                public int compare(Object o1, Object o2) {
                  return new Double((String) o1).compareTo(new Double((String) o2));
                }
              });
        } catch (Exception e) {
            Collections.sort(resultsKeys);
        }
        for (int i = 0; i < resultsKeys.size(); i++) {
            String key = resultsKeys.get(i);
            String[] peInfo = key.split("_");
            ThyroidScreeningData tsd = results.get(key);
            ThyroidNodulesData tnd = tsd.getThyroidNodulesData();
            List<NoduleData> list = tnd.getNoduleDataList();
            for (int j = 0; j < list.size(); j++) {
                NoduleData noduleData = list.get(j);
                int assessmentResult = RiskAssessmentUtils.assessmentLogic(noduleData);
                int suggestResult = RiskAssessmentUtils.suggestLogic(assessmentResult, noduleData.getNoduleSize());
                String tiradsResult = RiskAssessmentUtils.tiradsAssessmentLogic(noduleData);
                noduleData.setNoduleEstimateResult(assessmentResult + "");
                noduleData.setNoduleSuggest(suggestResult + "");
                noduleData.setNoduleTiradsResult(tiradsResult);
            }
            QueryData qd = new QueryData(tsd);
            qd.queryData();
            fileHandler.writeFileProcessing(peInfo, tsd);
        }
        fileHandler.closeWriteFileProcessing();
    }
    
    public static void singleAssessmentEnter(String text) {
        int assessmentResult;
        int suggestResult;
        
        FileHandler fileHandler = new FileHandler();
        ThyroidScreeningData tsd = new ThyroidScreeningData();
        ThyroidNodulesData tnd = fileHandler.analysisThyroidNodulesData(text);
        tsd.setThyroidNodulesData(tnd);
        
        List<NoduleData> list = tnd.getNoduleDataList();
        for (int j = 0; j < list.size(); j++) {
            NoduleData noduleData = list.get(j);
            assessmentResult = RiskAssessmentUtils.assessmentLogic(noduleData);
            suggestResult = RiskAssessmentUtils.suggestLogic(assessmentResult, noduleData.getNoduleSize());
            noduleData.setNoduleEstimateResult(assessmentResult + "");
            noduleData.setNoduleSuggest(suggestResult + "");
        }
        QueryData qd = new QueryData(tsd);
        qd.queryData();
        
        if (tsd.isThyroidDataIntegrityFlag()) {
            for (int i = 0; i < tsd.getThyroidNodulesData().getNodulesCount(); i++) {
                NoduleData noduleData = tsd.getThyroidNodulesData().getNoduleDataList().get(i);
                System.out.println(i + 1);
                System.out.println(noduleData.getNoduleEstimateResult() + " " + fileHandler.getAssessment()[Integer.parseInt(noduleData.getNoduleEstimateResult())]);
                System.out.println(noduleData.getNoduleSuggest());
                System.out.println(JSON.toJSONString(noduleData));
            }
            
        } else {
            NoNoduleData noNoduleData = tsd.getThyroidNodulesData().getNoNoduleData();
            System.out.println();
            System.out.println(noNoduleData.getThyroidBodySuggest());
            System.out.println(JSON.toJSONString(noNoduleData));
        }
    }
    
    public static Map<String, String> singleAssessmentEnter(NoduleData noduleData) {
        Map<String, String> result = new HashMap<String, String>();
        int assessmentResult = RiskAssessmentUtils.assessmentLogic(noduleData);
        int suggestResult = RiskAssessmentUtils.suggestLogic(assessmentResult, noduleData.getNoduleSize());
        String tiradsResult = RiskAssessmentUtils.tiradsAssessmentLogic(noduleData);
        result.put(ASSESSMENT_RESULT, assessmentResult + "");
        result.put(TIRADS_RESULT, tiradsResult);
        result.put(SUGGEST_RESULT, suggestResult + "");
        return result;
    }

}
