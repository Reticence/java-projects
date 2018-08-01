package com.meridian.testCode;

import com.alibaba.fastjson.JSON;
import com.meridian.module.OracleTransfer;
import com.meridian.nodules.EnterClass;
import com.meridian.nodules.model.NoduleData;
import com.meridian.param.DBParam;
import com.meridian.utils.MysqlConnectionPool;
import com.meridian.utils.DateUtil;
import com.meridian.utils.FileOperationUtil;
import com.meridian.utils.PoiUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCode {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCode.class);

    /** 全角对应于ASCII表的可见字符从！开始，偏移值为65281 **/
    private static final char SBC_CHAR_START = 65281; // 全角！

    /** 全角对应于ASCII表的可见字符到～结束，偏移值为65374 **/
    private static final char SBC_CHAR_END = 65374; // 全角～

    /** ASCII表中除空格外的可见字符与对应的全角字符的相对偏移 **/
    private static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    private static String desktopPath = "D:/Desktop/";

    /**
     * @param args a
     */
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        /** 非结构化数据内容导出 **/
        // createContents("T:/test/aaa/");

        /** 武汉市中心医院综述解析 **/
        // overviewAnalysis("D:/Desktop/whszxyy.txt",
        // "D:/Desktop/whszxyy_result.txt");

        /** 甲状腺结节风险评估 **/
        // MysqlConnect.init();
        // Constant constant = new Constant();
        // PdfDbMethod pdfDbMethod = new PdfDbMethod(constant);
        //
        // constant.conn = MysqlConnect.mysqlConnect("ultrasonography");
        // try {
        // constant.conn.setAutoCommit(false);
        // } catch (SQLException e1) {
        // e1.printStackTrace();
        // }
        //
        // RiskAssessment ra = new RiskAssessment(constant, pdfDbMethod);
        // ra.assessmentProcess(1179);

        /** tianFangDa数据整理 **/
        // 获取index
        // tianFangDa("D:\\Temporary Space\\Export-Folder\\湖北20原始报告", false,
        // false, true);
        // 获取原始数据
        // tianFangDa("T:/tfd/湖北中山医院/", false, false, true);
        // tianFangDa("T:/tfd/解放军161医院/", false, false, true);
        // tianFangDa("T:/tfd/荆门市第二人民医院/", false, false, true);
        // tianFangDa("T:/tfd/荆州市第一人民医院/", false, false, true);
        // tianFangDa("T:/tfd/荆州市中心医院/", false, false, true);
        // tianFangDa("T:/tfd/荆州市中医医院/", false, false, true);
        // tianFangDa("T:/tfd/荆州松滋市人民医院/", false, false, true);
        // tianFangDa("T:/tfd/石首市人民医院/", false, false, true);
        // tianFangDa("T:/tfd/襄阳东风人民医院/", false, false, true);
        // 获取分类数据
        // tianFangDa(sourcePath, true, false);

        /** tianFangDa去除姓名及单位 **/
        // tianFangDaDesensitization();

        /** 161医院流水号和文件号对应 **/
        // liuShuiHao_161YiYuan("");

        /** 161医院_超声 **/
        // chaoSheng_161YiYuan("D:/Desktop/161医院_超声.xlsx");

        /** MyTest Code **/
        // String sourceStr = "双颈动脉硬化多发斑块形成、左侧颈动脉局部狭窄";
        // String tmpStr;
        // int a = sourceStr.indexOf("左");
        // int b = sourceStr.indexOf("右");
        // int c = sourceStr.indexOf("双");
        // if (a > 0) {
        // tmpStr = sourceStr.substring(a);
        // }
        // LOGGER.info(a + " " + b + " " + c);

        // 2016年11月5日 整理161测试数据
        // String folderPath = "D:/Desktop/zhengli_1109/";
        // zhengLi_1105(folderPath);
        // zhengli_1106(folderPath);
        // zhengli_1109(folderPath);
        // zhengli_1116();
        // jiaZhuangXianChaoShengJiSuan();
        // chaoshenghebing_1118("D:/Desktop/超声已审共25个文件");

        // testMethod();

        // List<String> list = new ArrayList<String>();
        // list.add("000");
        // list.add("111");
        // list.add("222");
        // list.add("333");
        // list.set(2, list.get(2) + "change");
        // for (String string : list) {
        // LOGGER.info(string);
        // }

        // threadPoolTest();
        // MysqlConnectionPool connectionPool = new MysqlConnectionPool();

        /** 甲状腺结节评估 **/
        // EnterClass.batchAssessmentEnter(desktopPath +
        // "天津医院2017.1.1-7.31.xlsx", 0,
        // null, 6);
        // EnterClass.singleAssessmentEnter(TextReader.getReader().getText());

        // NoduleData noduleData = new NoduleData();
        // noduleData.setNoduleStructure("实性");
        // noduleData.setNoduleSecondStructure("");
        // noduleData.setNoduleHyperecho("高回声");
        // noduleData.setNoduleInvasion("有腺体外侵犯");
        // noduleData.setNoduleEdge("边缘不规则");
        // noduleData.setNoduleRatio("＜1");
        // noduleData.setNoduleCalcification("无钙化");
        // noduleData.setNoduleSize("0.6");
        // Map<String, String> map =
        // EnterClass.singleAssessmentEnter(noduleData);
        // for (String key : map.keySet()) {
        // LOGGER.info(key + " = " + map.get(key));
        // }

        /* 空军总医院数据提取 */
        // task20171013("T:/kongzong/");

        // updateTiradsResult();

        // continuousData();

//        final Set<String> targets = new HashSet<String>();
//        String[] sids = { "gbnew", "gbold", "gjnew", "gjold" };
////        String[] sids = { "gjnew" };
//        List<Thread> threads = new ArrayList<Thread>();
//        for (final String sid : sids) {
//            final DBParam dbp = new DBParam();
//            dbp.setHost("10.1.1.102");
//            dbp.setPort("1521");
//            dbp.setUsername("phyexam");
//            dbp.setPassword("meridian");
//            dbp.setSid(sid);
//            final DBParam tdbp = new DBParam();
//            tdbp.setHost("10.1.1.102");
//            tdbp.setPort("1521");
//            tdbp.setPassword("meridian");
//            tdbp.setSid("orcl");
//            tdbp.setUsername(sid);
//            Runnable runnable = new Runnable() {
//                public void run() {
//                    OracleTransfer ot = new OracleTransfer(dbp);
//                    ot.set(targets, "jfjzyy301_" + sid, 50000);  // 50000
//                    ot.start2Oracle(tdbp, 32);
////                    ot.start2Mysql(18);
//                }
//            };
//            Thread thread = new Thread(runnable);
//            threads.add(thread);
//            thread.start();
//        }
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        DBParam dbp = new DBParam();
        dbp.setHost("10.1.1.102");
        dbp.setPort("1521");
        dbp.setUsername("tjxt");
        dbp.setPassword("meridian");
        dbp.setSid("orcl");
//        DBParam tdbp = new DBParam();
//        tdbp.setHost("10.1.1.102");
//        tdbp.setPort("1521");
//        tdbp.setUsername("tjxt");
//        tdbp.setPassword("meridian");
//        tdbp.setSid("orcl");
        Set<String> targets = new HashSet<String>();
//        targets.add("PE_DIAGNOSIS_RECORD");
//        targets.add("PE_GUIDE_RESULT");
        OracleTransfer ot = new OracleTransfer(dbp);
        ot.set(targets, "jnyxyyzfy", 50000);
        ot.setCharacterSetConversion(false);
//        ot.start2Oracle(tdbp, 32);
        ot.start2Mysql(18);

        System.out.println(getMemoryInfo(begin));
    }

    /**
     * 空军总医院数据提取
     * 
     * @author Reticence (liuyang_blue@qq.com)
     */
    public static void task20171013(String sourcePath) {
        List<File> files = FileOperationUtil.fileRecurses(sourcePath);
        Workbook wbw = new SXSSFWorkbook();
        Sheet sw = wbw.createSheet("export");
        Row rw;
        Map<String, Integer> title2num = new HashMap<String, Integer>();
        String title;
        String value;
        int sumRowNum = 1;
        int sumColNum;
        for (int i = 0; i < files.size(); i++) {
            Map<Integer, String> num2title = new HashMap<Integer, String>();
            try {
                Workbook wbr;
                File file = files.get(i);
                if (file.getName().endsWith(".xls")) {
                    wbr = new HSSFWorkbook(new FileInputStream(file));
                } else if (file.getName().endsWith(".xlsx")) {
                    wbr = new XSSFWorkbook(new FileInputStream(file));
                } else {
                    continue;
                }
                Sheet sr = wbr.getSheetAt(0);
                Row rr = sr.getRow(0);
                for (int colNum = 0; colNum <= rr.getLastCellNum(); colNum++) {
                    value = PoiUtil.getValue(rr.getCell(colNum));
                    if (StringUtils.isNotBlank(value)) {
                        num2title.put(colNum, value);
                    }
                }
                for (int rowNum = 1; rowNum <= sr.getLastRowNum(); rowNum++) {
                    rr = sr.getRow(rowNum);
                    if (rr == null) {
                        continue;
                    }
                    rw = sw.createRow(sumRowNum);
                    sumRowNum++;
                    for (int colNum = 0; colNum <= rr.getLastCellNum(); colNum++) {
                        value = PoiUtil.getValue(rr.getCell(colNum));
                        if (StringUtils.isNotBlank(value)) {
                            title = num2title.get(colNum);
                            if (null == title) {
                                title = "终检";
                            }
                            if (!title2num.containsKey(title)) {
                                title2num.put(title, title2num.size());
                            }
                            sumColNum = title2num.get(title);
                            if (sumColNum == 5) {
                                try {
                                    value = DateUtil.getDates(value);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            PoiUtil.setCellValue(rw, sumColNum, value);
                        }
                    }
                }
                wbr.close();
            } catch (IOException e) {
                LOGGER.error(files.get(i).getPath());
            }
            LOGGER.info(title2num.size() + "  " + files.get(i).getPath());
        }
        Workbook wbw2 = new SXSSFWorkbook();
        Sheet sw2 = wbw2.createSheet("export");
        rw = sw2.createRow(0);
        for (String key : title2num.keySet()) {
            if (StringUtils.isBlank(key)) {
                System.out.println();
            }
            PoiUtil.setCellValue(rw, title2num.get(key), key);
        }

        String excelPath = sourcePath;
        if (excelPath.endsWith("/")) {
            excelPath = sourcePath.substring(0, sourcePath.length() - 1);
        }
        String excelPath2 = excelPath;
        excelPath += ".xlsx";
        excelPath2 += "_title.xlsx";
        try {
            FileOutputStream fos;
            fos = new FileOutputStream(excelPath);
            wbw.write(fos);
            wbw.close();
            fos.flush();
            fos.close();
            fos = new FileOutputStream(excelPath2);
            wbw2.write(fos);
            wbw2.close();
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void continuousData() {
        String path = "T:/gx_人数判断_1.xlsx";
        try {
            FileInputStream inputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            inputStream.close();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                LOGGER.info(workbook.getSheetName(i));
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void updateTiradsResult() {
        MysqlConnectionPool mysqlConnectionPool = new MysqlConnectionPool(5);
        String sql = "SELECT A.orgID, A.PE_ID, B.checkdate, A.noduleNum, A.structure, A.second_structure, A.hyperecho, A.edge, A.ratio, A.calcification"
                + " FROM ultrasonography.data_info A"
                + " LEFT JOIN ultrasonography.base_info B ON A.orgID = B.orgID AND A.PE_ID = B.PE_ID";
        List<String[]> list = mysqlConnectionPool.execQuery(sql);
        for (int i = 0; i < list.size(); i++) {
            String[] arr = list.get(i);
            NoduleData noduleData = new NoduleData();
            noduleData.setNoduleStructure(arr[4]);
            noduleData.setNoduleSecondStructure(arr[5]);
            noduleData.setNoduleHyperecho(arr[6]);
            noduleData.setNoduleEdge(arr[7]);
            noduleData.setNoduleRatio(arr[8]);
            noduleData.setNoduleCalcification(arr[9]);
            Map<String, String> map = EnterClass.singleAssessmentEnter(noduleData);
            LOGGER.info(map.get(EnterClass.TIRADS_RESULT) + "\t" + JSON.toJSONString(noduleData));
            StringBuffer updateSql = new StringBuffer();
            // String checkdate = StringUtils.isBlank(arr[2]) ? "IS NULL" : "=
            // '" + arr[2] +
            // "'";
            // updateSql.append("UPDATE
            // thyroid_nodulesdb.sys_weixin_assessment_result SET
            // tiradsResult = '" + map.get(EnterClass.TIRADS_RESULT) + "'");
            // updateSql.append(" WHERE orgID = " + arr[0] + " AND checkNumber =
            // '" + arr[1]
            // + "' AND checkdate " + checkdate + " AND noduleNum = " + arr[3]);
            updateSql.append("UPDATE thyroid_nodulesdb.sys_weixin_assessment_result SET tiradsResult = '").append(map.get(EnterClass.TIRADS_RESULT)).append("'");
            updateSql.append(" WHERE orgID = ").append(arr[0]).append(" AND checkNumber = '").append(arr[1]).append("' AND noduleNum = ").append(arr[3]);
            boolean execResult = false;
            execResult = mysqlConnectionPool.execUpdate(updateSql.toString());
            LOGGER.info(execResult + "\t" + updateSql.toString());
        }
        mysqlConnectionPool.close();
    }

    public static void threadPoolTest() {
        MysqlConnectionPool mysqlConnectionPool = new MysqlConnectionPool();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<String>> taskList = new ArrayList<Future<String>>();
        for (int i = 0; i < 100; i++) {
            ThreadPoolTask task = new ThreadPoolTask(mysqlConnectionPool, "SELECT " + i + ", index_code, index_name FROM checkup_library.meridian_index_list");
            Future<String> future = executorService.submit(task);
            taskList.add(future);
        }
        StringBuffer allExecuteResult = new StringBuffer();
        for (Future<String> future : taskList) {
            try {
                allExecuteResult.append(future.get()).append("\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        LOGGER.info(allExecuteResult.toString());
    }

    public static String plusDay(String appointedDay, int num) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currdate = format.parse(appointedDay);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);
        currdate = ca.getTime();
        return format.format(currdate);
    }

    public static void testMethod() {
        Pattern pattern1 = Pattern.compile("[0-9]+(\\.[0-9]+)?×[0-9]+(\\.[0-9]+)?×[0-9]+(\\.[0-9]+)?cm");
        Pattern pattern2 = Pattern.compile("[0-9]+(\\.[0-9]+)?×[0-9]+(\\.[0-9]+)?cm");
        Pattern pattern3 = Pattern.compile("[0-9]+(\\.[0-9]+)?cm");

        String inPath = "D:/Desktop/input.txt";
        String outPath = "D:/Desktop/output.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(inPath)));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPath), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("x", "×").replace("X", "×").replace("*", "×");
                List<String> oldStrings;

                oldStrings = getMatcherList(pattern1, line);
                line = cm2mm(line, oldStrings);
                oldStrings = getMatcherList(pattern2, line);
                line = cm2mm(line, oldStrings);
                oldStrings = getMatcherList(pattern3, line);
                line = cm2mm(line, oldStrings);

                bw.write(line);
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String cm2mm(String string, List<String> oldStrings) {
        Pattern patternNum = Pattern.compile("[0-9]+(\\.[0-9]+)?");
        for (String oldString : oldStrings) {
            StringBuilder newString = new StringBuilder();
            List<String> oldNums = getMatcherList(patternNum, oldString);
            List<Integer> newNums = new ArrayList<Integer>();
            for (String oldNum : oldNums) {
                Integer newNum = Integer.parseInt(oldNum.replace(".", ""));
                newNums.add(newNum);
            }
            Collections.sort(newNums, Collections.reverseOrder());
            for (Integer integer : newNums) {
                newString.append("×").append(integer);
            }
            newString = new StringBuilder(newString.substring(1) + "mm");
            string = string.replace(oldString, newString.toString());
        }
        return string;
    }

    public static int strFindCount(String str, String findStr) {
        int count = 0;
        int index;
        while ((index = str.indexOf(findStr)) != -1) {
            count++;
            str = str.substring(index + findStr.length());
        }
        return count;
    }

    private static List<String> getMatcherList(Pattern pattern, String oldString) {
        List<String> oldStrings = new ArrayList<String>();
        Matcher matcher = pattern.matcher(oldString);
        while (matcher.find()) {
            oldStrings.add(matcher.group());
        }
        return oldStrings;
    }

    public static void chaoshenghebing_1118(String folderPath) {
        String outFilePath = "D:/Desktop/超声25_result.xlsx";
        try {
            Workbook workbookR = new SXSSFWorkbook();
            Sheet sheetR = workbookR.createSheet();
            Row rowR;
            int writeRownum = 0;

            File root = new File(folderPath);
            File[] files = root.listFiles();
            for (File file : files) {
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0);
                Row row;

                int lastRowNum = sheet.getLastRowNum();
                for (int rownum = 0; rownum <= lastRowNum; rownum++) {
                    row = sheet.getRow(rownum);
                    if (null == row || StringUtils.isBlank(PoiUtil.getValue(row.getCell(0)))) {
                        continue;
                    }
                    rowR = sheetR.createRow(writeRownum++);
                    rowR.createCell(0, CellType.STRING).setCellValue(FileOperationUtil.getFileNameNoEx(file.getName()));
                    rowR.createCell(1, CellType.STRING).setCellValue(PoiUtil.getValue(row.getCell(0)));
                    rowR.createCell(2, CellType.STRING).setCellValue(PoiUtil.getValue(row.getCell(1)));
                    rowR.createCell(3, CellType.STRING).setCellValue(PoiUtil.getValue(row.getCell(2)));
                }
                workbook.close();
            }

            FileOutputStream outputStream = new FileOutputStream(outFilePath);
            workbookR.write(outputStream);
            workbookR.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public static void jiaZhuangXianChaoShengJiSuan() {
        String inputFile = "D:/Desktop/jiazhuangxian.txt";
        String outputFile = "D:/Desktop/jiazhuangxian_count.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "GBK"));
            Map<String, Integer> countMap = new HashMap<String, Integer>();
            String line;
            while ((line = br.readLine()) != null) {
                if (StringUtils.isBlank(line))
                    continue;
                String[] keys = line.split(";");
                for (String key : keys) {
                    if (StringUtils.isNotBlank(key)) {
                        if (null == countMap.get(key)) {
                            countMap.put(key, 1);
                        } else {
                            countMap.put(key, countMap.get(key) + 1);
                        }
                    }
                }
            }
            br.close();
            for (String key : countMap.keySet()) {
                bw.write(key + "," + countMap.get(key));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zhengli_1116() {
        String inputFile = "D:/Desktop/湖北中山医院-超声原始数据.xlsx";
        String outputFile = "D:/Desktop/湖北中山医院-超声原始数据-out.xlsx";
        try {
            Map<String, String> formatMap = new HashMap<String, String>();
            Workbook workbook = new XSSFWorkbook(inputFile);
            Sheet sheet;
            Row row;
            String key;
            String value;

            sheet = workbook.getSheet("Sheet2");
            for (int rownum = 0; rownum <= sheet.getLastRowNum(); rownum++) {
                row = sheet.getRow(rownum);
                if (null == row)
                    continue;
                key = PoiUtil.getValue(row.getCell(0));
                value = PoiUtil.getValue(row.getCell(1));
                formatMap.put(key, value);
            }

            Workbook outWorkbook = new SXSSFWorkbook();
            Sheet outSheet = outWorkbook.createSheet("outSheet");
            Row outRow;
            sheet = workbook.getSheet("Sheet1");
            for (int rownum = 0; rownum <= sheet.getLastRowNum(); rownum++) {
                row = sheet.getRow(rownum);
                if (null == row)
                    continue;
                outRow = outSheet.createRow(rownum);
                outRow.createCell(0, CellType.STRING).setCellValue(PoiUtil.getValue(row.getCell(0)));
                int outColnum = 1;
                for (int colnum = 1; colnum <= row.getLastCellNum(); colnum++) {
                    key = PoiUtil.getValue(row.getCell(colnum));
                    if (StringUtils.isBlank(key)) {
                        key = "";
                    }
                    outRow.createCell(outColnum++, CellType.STRING).setCellValue(key);

                    value = formatMap.get(key);
                    if (StringUtils.isBlank(value) && rownum > 1 && StringUtils.isNotBlank(key)) {
                        value = "None";
                    }
                    outRow.createCell(outColnum++, CellType.STRING).setCellValue(value);
                }
            }
            workbook.close();

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outWorkbook.write(outputStream);
            outWorkbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteQuanJiao(String inPath) {
        String outPath = inPath + "_new.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(inPath)));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outPath)));
            String line;
            while ((line = br.readLine()) != null) {
                line = convert(line);
                bw.write(line);
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zhengli_1109(String folderPath) {
        String outPath = folderPath + "outfile.txt";
        String sourceFolder = folderPath + "input/";
        String notFindFolder = folderPath + "notfind/";
        String name2CodeFilePath = folderPath + "index_name_to_code.txt";
        try {
            Map<String, String> name2CodeMap = new HashMap<String, String>();
            BufferedReader reader = new BufferedReader(new FileReader(new File(name2CodeFilePath)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split("\t");
                strings[1] = convert(strings[1]);
                name2CodeMap.put(strings[0], strings[1]);
            }
            reader.close();

            File root;
            File[] files;
            root = new File(notFindFolder);
            files = root.listFiles();
            for (File file : files) {
                file.delete();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(outPath, false));
            root = new File(sourceFolder);
            files = root.listFiles();
            for (File file : files) {
                LOGGER.info(file.getPath());
                Workbook workbook = new XSSFWorkbook(file);
                int maxSheetNum = workbook.getNumberOfSheets() - 1;
                Sheet sheet = workbook.getSheet("原始数据");
                Row row;
                if (null == sheet) {
                    LOGGER.info("Sheet(原始数据)不存在");
                    continue;
                }
                String indexName = PoiUtil.getValue(sheet.getRow(0).getCell(1));
                String indexCode = name2CodeMap.get(indexName);
                if (indexCode != null) {
                    boolean runFlag = false;
                    do {
                        maxSheetNum--;
                        sheet = workbook.getSheetAt(maxSheetNum);
                        String tmp = PoiUtil.getValue(sheet.getRow(0).getCell(1));
                        if ("编码".equals(tmp)) {
                            runFlag = true;
                            break;
                        }
                    } while (maxSheetNum > 0);

                    if (runFlag) {
                        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
                            row = sheet.getRow(rownum);
                            if (null == row) {
                                continue;
                            }
                            Cell cell_0 = row.getCell(0);
                            Cell cell_1 = row.getCell(1);
                            Cell cell_2 = row.getCell(2);
                            Cell cell_3 = row.getCell(3);
                            if (null == cell_0 || null == cell_1 || (null == cell_2 && null == cell_3)) {
                                continue;
                            }

                            String codeName = PoiUtil.getValue(cell_0);
                            String codeValue = PoiUtil.getValue(cell_1);
                            String value;
                            String tmp;

                            if (null == cell_2) {
                                codeName = StringUtils.isNotBlank(tmp = PoiUtil.getValue(cell_3)) ? tmp : codeName;
                            }
                            if (null == cell_3) {
                                codeValue = StringUtils.isNotBlank(tmp = PoiUtil.getValue(cell_2)) ? tmp : codeValue;
                            } else {
                                codeName = StringUtils.isNotBlank(tmp = PoiUtil.getValue(cell_3)) ? tmp : codeName;
                                codeValue = StringUtils.isNotBlank(tmp = PoiUtil.getValue(cell_2)) ? tmp : codeValue;
                            }
                            try {
                                value = codeName + "\t" + indexCode + "-" + "0000".substring(codeValue.length()) + codeValue;
                                // LOGGER.info(value);
                                writer.write(value);
                                writer.newLine();
                            } catch (StringIndexOutOfBoundsException e) {
                                LOGGER.info("\tcodeName=" + codeName + "\tindexCode=" + indexCode + "\tcodeValue" + codeValue);
                            }
                        }
                    }
                } else {
                    LOGGER.info("indexName=" + indexName);
                    copyFile(file, notFindFolder + file.getName());
                }
                workbook.close();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public static void zhengli_1106(String folderPath) {
        String name2CodeFilePath = folderPath + "index_name_to_code.txt";
        String sourceFolder = folderPath + "input/";
        String codeClassifyFilePath = folderPath + "code_classify.txt";
        try {
            Map<String, String> name2CodeMap = new HashMap<String, String>();
            BufferedReader reader = new BufferedReader(new FileReader(new File(name2CodeFilePath)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split("\t");
                strings[1] = convert(strings[1]);
                name2CodeMap.put(strings[0], strings[1]);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(codeClassifyFilePath, false));

            File root = new File(sourceFolder);
            File[] files = root.listFiles();

            for (File file : files) {
                LOGGER.info(file.getPath());
                Workbook workbook = new XSSFWorkbook(file);
                int maxSheetNum = workbook.getNumberOfSheets() - 1;
                Sheet sheet = workbook.getSheetAt(maxSheetNum);
                Row row;
                String indexName = PoiUtil.getValue(sheet.getRow(0).getCell(1));
                String indexCode = name2CodeMap.get(indexName);
                String value;
                if (indexCode != null) {
                    String tmp;
                    boolean runFlag = false;
                    do {
                        maxSheetNum--;
                        sheet = workbook.getSheetAt(maxSheetNum);
                        tmp = PoiUtil.getValue(sheet.getRow(0).getCell(1));
                        if ("编码".equals(tmp)) {
                            runFlag = true;
                            break;
                        }
                    } while (maxSheetNum > 0);

                    if (runFlag) {
                        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
                            row = sheet.getRow(rownum);
                            if (null == row) {
                                continue;
                            }
                            Cell cell = row.getCell(1);
                            if (null == cell) {
                                continue;
                            }
                            String code = PoiUtil.getValue(cell);
                            try {
                                value = PoiUtil.getValue(row.getCell(0)) + "|$" + indexCode + "-" + "0000".substring(code.length()) + code + "^$^";
                                writer.write(value);
                            } catch (StringIndexOutOfBoundsException e) {
                                LOGGER.info("indexCode=" + indexCode + "\tcode=" + code);
                            }
                        }
                    }
                } else {
                    LOGGER.info("indexName=" + indexName);
                }
                workbook.close();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public static void zhengLi_1105(String folderPath) {
        String name2CodeFilePath = folderPath + "index_name_to_code.txt";
        String codeContentFilePath = folderPath + "code_content.txt";
        String outFilePath = folderPath + "zhengli.xlsx";
        String sourceFolder = folderPath + "output/";
        try {
            Map<String, String> name2CodeMap = new HashMap<String, String>();
            // BufferedWriter codeWriter = new BufferedWriter(new
            // FileWriter(name2CodeFilePath.replace(".txt", "_new.txt"), true));
            BufferedReader reader = new BufferedReader(new FileReader(new File(name2CodeFilePath)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split("\t");
                strings[1] = convert(strings[1]);
                name2CodeMap.put(strings[0], strings[1]);
                // codeWriter.write(strings[0] + "\t" + strings[1]);
                // codeWriter.newLine();
            }
            reader.close();
            // codeWriter.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(codeContentFilePath, false));
            Set<String> codeContentSet = new HashSet<String>();

            Workbook workbookR = new XSSFWorkbook();
            Sheet sheetR = workbookR.createSheet();
            Row firstRowR = sheetR.createRow(0);
            Row rowR;
            boolean firstFileFlag = true;

            File root = new File(sourceFolder);
            File[] files = root.listFiles();

            int maxRowNum = 0;
            int column = 1;
            String indexName;
            String indexCode;
            String value;
            StringBuilder codeConten;
            firstRowR.createCell(0).setCellValue("登记流水号");
            for (File file : files) {
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0);
                Row row;
                int lastRowNum = sheet.getLastRowNum();
                LOGGER.info("-------".substring((lastRowNum + "").length()) + lastRowNum + "  " + file.getPath());
                if (maxRowNum > 0 && lastRowNum > maxRowNum) {
                    lastRowNum = maxRowNum;
                } else if (lastRowNum == 0) {
                    continue;
                }
                indexName = PoiUtil.getValue(sheet.getRow(0).getCell(1));
                indexCode = name2CodeMap.get(indexName);
                firstRowR.createCell(column).setCellValue(indexName);
                boolean printFlag = false;
                for (int rownum = 1; rownum <= lastRowNum; rownum++) {
                    row = sheet.getRow(rownum);
                    if (firstFileFlag) {
                        rowR = sheetR.createRow(rownum);
                        value = PoiUtil.getValue(row.getCell(0));
                        rowR.createCell(0).setCellValue(value);
                        maxRowNum = lastRowNum;
                    } else {
                        rowR = sheetR.getRow(rownum);
                    }
                    value = PoiUtil.getValue(row.getCell(1));
                    rowR.createCell(column).setCellValue(value);
                    if (!"".equals(value)) {
                        codeConten = new StringBuilder(value + "|$");
                        value = PoiUtil.getValue(row.getCell(2));
                        if (value.contains("#%#")) {
                            printFlag = true;
                        }
                        String[] codes = value.split(";");
                        for (String code : codes) {
                            int dotNUm = code.indexOf(".");
                            if (dotNUm > 0) {
                                code = code.substring(0, dotNUm);
                            }
                            try {
                                codeConten.append(indexCode).append("-").append("0000".substring(code.length())).append(code).append(";");
                            } catch (StringIndexOutOfBoundsException e) {
                                LOGGER.info("indexCode=" + indexCode + "\tcode=" + code);
                            }

                        }
                        codeConten = new StringBuilder(codeConten.substring(0, codeConten.length() - 1));
                        if (!codeContentSet.contains(codeConten.toString())) {
                            codeContentSet.add(codeConten.toString());
                            writer.write(codeConten + "^$^");
                        }
                    }
                }
                if (indexCode == null || printFlag) {
                    LOGGER.info("\tindexName=" + indexName + "\tindexCode=" + indexCode + "\tprintFlag=" + !printFlag);
                }
                column++;
                firstFileFlag = false;
                workbook.close();
            }
            writer.close();

            FileOutputStream outputStream = new FileOutputStream(outFilePath);
            workbookR.write(outputStream);
            workbookR.close();
            // workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public static void chaoSheng_161YiYuan(String sourcePath) {
        String handledPath = sourcePath.substring(0, sourcePath.length() - 5) + "_已处理.xlsx";

        File file = new File(handledPath);
        if (file.exists()) {
            file.delete();
        }

        String describe = "(声像图)?(未见异常|未见明显异常)";
        try {
            LOGGER.info("start");
            Workbook workbook_r = new XSSFWorkbook(new FileInputStream(sourcePath));
            Sheet sheet_r = workbook_r.getSheetAt(0);

            Workbook workbook_w = new XSSFWorkbook();
            Sheet sheet_w = workbook_w.createSheet("handled data");
            Row row_w;

            String sourceValue;
            String handledValue;
            String handledValuesTmp;
            String[] handledValues;
            sourceValue = sheet_r.getRow(0).getCell(0).getStringCellValue();
            row_w = sheet_w.createRow(0);
            row_w.createCell(0, CellType.STRING).setCellValue(sourceValue);
            int colNUm;
            int maxColNUm = 0;
            int splitNum;
            String punctuation;
            TreeSet<String> handledResult = new TreeSet<String>();
            TreeSet<String> tmpResult = new TreeSet<String>();

            ArrayList<String> titalList = new ArrayList<String>();
            HashMap<String, Integer> titalMap = new HashMap<String, Integer>();
            titalList.add("肝");
            titalList.add("总胆管");
            titalList.add("胆总管");
            titalList.add("胆囊");
            titalList.add("胆");
            titalList.add("脾");
            titalList.add("肾");
            titalList.add("前列腺");
            titalList.add("胰");
            titalList.add("膀胱");
            titalList.add("绝经后子宫萎缩");
            titalList.add("绝经后子宫");
            titalList.add("后位子宫");
            titalList.add("子宫肌瘤");
            titalList.add("子宫");
            titalList.add("盆腔");
            titalList.add("附件");
            titalList.add("甲状腺");
            titalList.add("颌下线");
            titalList.add("腮腺");
            titalList.add("椎动脉");
            titalList.add("颈动脉系");
            titalList.add("乳腺");
            titalList.add("心脏形态结构");
            titalList.add("CDFI");
            for (String str : titalList) {
                titalMap.put(str, ++maxColNUm);
                row_w.createCell(maxColNUm, CellType.STRING).setCellValue(str);
            }

            for (int i = 1; i < sheet_r.getLastRowNum(); i++) {
                punctuation = ".";
                row_w = sheet_w.createRow(i);
                handledResult.clear();
                sourceValue = sheet_r.getRow(i).getCell(0).getStringCellValue();
                // LOGGER.info(sourceValue);
                row_w.createCell(0, CellType.STRING).setCellValue(sourceValue);

                handledValues = sourceValue.replaceAll("\r\n|\r|\n", "#@#").replaceAll("\t", "#@#").replaceAll("　", "#@#").replaceAll(" ", "#@#")
                        // .replaceAll("，", "#@#")
                        .replaceAll("甲状腺：", "#@#").replaceAll("腹部：", "#@#").replaceAll("心脏：", "#@#").replaceAll("妇科：", "#@#").replaceAll("乳腺：", "#@#").replaceAll("颈部：", "#@#").split("#@#");
                for (String value : handledValues) {
                    value = value.trim();
                    if ("".equals(value)) {
                        continue;
                    }
                    handledValuesTmp = matchingStr(value, describe);

                    while (".".equals(punctuation) || "、".equals(punctuation)) {
                        splitNum = 2;
                        if (value.contains("1" + punctuation)) {
                            while (value.contains(splitNum + punctuation)) {
                                handledValue = value.substring(0, value.indexOf(splitNum + punctuation));
                                value = value.substring(value.indexOf(splitNum + punctuation));
                                // LOGGER.info("while value = " +
                                // handledValue);
                                handledResult.add(handledValue);
                                splitNum++;
                            }
                            if ("".equals(handledValuesTmp)) {
                                // LOGGER.info("while value = " + value);
                                handledResult.add(value);
                            }
                        }
                        if ("、".equals(punctuation)) {
                            break;
                        }
                        punctuation = "、";
                    }

                    if (!"".equals(handledValuesTmp)) {
                        if (value.contains("肝")) {
                            handledResult.add("肝" + handledValuesTmp);
                        }
                        if (value.contains("胆")) {
                            if (value.contains("管")) {
                                handledResult.add("胆总管" + handledValuesTmp);
                            } else if (value.contains("囊")) {
                                handledResult.add("胆囊" + handledValuesTmp);
                            } else {
                                handledResult.add("胆" + handledValuesTmp);
                            }
                        }
                        if (value.contains("脾")) {
                            handledResult.add("脾" + handledValuesTmp);
                        }
                        if (value.contains("双肾")) {
                            if (value.contains("双")) {
                                handledResult.add("双肾" + handledValuesTmp);
                            } else if (value.contains("左")) {
                                handledResult.add("左肾" + handledValuesTmp);
                            } else if (value.contains("右")) {
                                handledResult.add("右肾" + handledValuesTmp);
                            } else {
                                handledResult.add("肾" + handledValuesTmp);
                            }
                        }
                        if (value.contains("前列腺")) {
                            handledResult.add("前列腺" + handledValuesTmp);
                        }
                        if (value.contains("胰")) {
                            handledResult.add("胰" + handledValuesTmp);
                        }
                        if (value.contains("膀胱")) {
                            handledResult.add("膀胱" + handledValuesTmp);
                        }
                        if (value.contains("子宫")) {
                            if (value.contains("绝经后")) {
                                if (value.contains("萎缩")) {
                                    handledResult.add("绝经后子宫萎缩");
                                } else {
                                    handledResult.add("绝经后子宫" + handledValuesTmp);
                                }
                            } else if (value.contains("后位")) {
                                handledResult.add("后位子宫");
                            } else if (value.contains("肌瘤")) {
                                handledResult.add("子宫肌瘤");
                            } else {
                                handledResult.add("子宫" + handledValuesTmp);
                            }
                        }
                        if (value.contains("盆腔")) {
                            handledResult.add("盆腔" + handledValuesTmp);
                        }
                        if (value.contains("附件")) {
                            if (value.contains("双侧")) {
                                handledResult.add("双侧附件" + handledValuesTmp);
                            } else if (value.contains("左侧")) {
                                handledResult.add("左侧附件" + handledValuesTmp);
                            } else if (value.contains("右侧")) {
                                handledResult.add("右侧附件" + handledValuesTmp);
                            } else {
                                handledResult.add("附件" + handledValuesTmp);
                            }
                        }
                        if (value.contains("甲状腺")) {
                            handledResult.add("甲状腺" + handledValuesTmp);
                        }
                        if (value.contains("颌下线")) {
                            handledResult.add("颌下线" + handledValuesTmp);
                        }
                        if (value.contains("腮腺")) {
                            handledResult.add("腮腺" + handledValuesTmp);
                        }
                        if (value.contains("椎动脉")) {
                            handledResult.add("椎动脉" + handledValuesTmp);
                        }
                        if (value.contains("颈动脉系")) {
                            if (value.contains("双侧")) {
                                handledResult.add("双侧颈动脉系" + handledValuesTmp);
                            } else if (value.contains("左侧")) {
                                handledResult.add("左侧颈动脉系" + handledValuesTmp);
                            } else if (value.contains("右侧")) {
                                handledResult.add("右侧颈动脉系" + handledValuesTmp);
                            } else {
                                handledResult.add("颈动脉系" + handledValuesTmp);
                            }
                        }
                        if (value.contains("乳腺")) {
                            if (value.contains("双侧")) {
                                handledResult.add("双侧乳腺" + handledValuesTmp);
                            } else if (value.contains("左侧")) {
                                handledResult.add("左侧乳腺" + handledValuesTmp);
                            } else if (value.contains("右侧")) {
                                handledResult.add("右侧乳腺" + handledValuesTmp);
                            } else {
                                handledResult.add("乳腺" + handledValuesTmp);
                            }
                        }
                        if (value.contains("心脏形态结构")) {
                            handledResult.add("心脏形态结构" + handledValuesTmp);
                        }
                        if (value.contains("CDFI")) {
                            handledResult.add("CDFI" + handledValuesTmp);
                        }
                        tmpResult.add(value);
                    } else {
                        handledResult.add(value);
                    }
                }
                // LOGGER.info();
                for (String string : handledResult) {
                    colNUm = 0;
                    for (String key : titalList) {
                        if (string.contains(key)) {
                            colNUm = titalMap.get(key);
                            row_w.createCell(colNUm, CellType.STRING).setCellValue(string);
                            break;
                        }
                    }
                    if (colNUm == 0) {
                        row_w.createCell(++maxColNUm, CellType.STRING).setCellValue(string);
                    }
                }
            }

            for (String string : tmpResult) {
                LOGGER.info(string);
            }

            workbook_r.close();

            FileOutputStream outputStream = new FileOutputStream(handledPath, true);
            workbook_w.write(outputStream);
            workbook_w.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String matchingStr(String str, String patternStr) {
        StringBuilder returnStr = new StringBuilder();
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            returnStr.append(matcher.group());
        }

        return returnStr.toString();
    }

    public static void liuShuiHao_161YiYuan(String sourcePath) {
        try {
            String excelPath = sourcePath.substring(0, sourcePath.length() - 1) + "_liushuihao.xlsx";

            File file = new File(excelPath);
            if (file.exists()) {
                file.delete();
            }

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("export data");
            Row titalRow = sheet.createRow(0);
            Row row;

            titalRow.createCell(0).setCellValue("登记流水号");
            titalRow.createCell(1).setCellValue("文件号");

            File root = new File(sourcePath);
            File[] files = root.listFiles();
            Workbook wbr;
            Sheet sheetr;

            String liuShuiHao;
            String wenJianHao;
            for (int i = 0; i < files.length; i++) {
                if (!files[i].getPath().endsWith(".xls")) {
                    continue;
                }
                LOGGER.info(files[i].getPath());
                wenJianHao = files[i].getName();
                wenJianHao = wenJianHao.substring(0, wenJianHao.indexOf("-"));

                wbr = new HSSFWorkbook(new FileInputStream(files[i]));
                sheetr = wbr.getSheetAt(0);
                liuShuiHao = sheetr.getRow(1).getCell(0).getStringCellValue().replace("登记流水号：", "");
                liuShuiHao = liuShuiHao.substring(0, liuShuiHao.indexOf("性别：")).trim();

                row = sheet.createRow(i + 1);
                row.createCell(0, CellType.STRING).setCellValue(liuShuiHao);
                row.createCell(1, CellType.STRING).setCellValue(wenJianHao);
            }

            FileOutputStream outputStream = new FileOutputStream(excelPath, true);
            wb.write(outputStream);
            wb.close();
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tianFangDaDesensitization() {
        // String sourcePath =
        // TestCode.class.getResource("").getPath().substring(1);
        String sourcePath = "./";
        LOGGER.info("sourcePath = " + sourcePath);
        // sourcePath = "D:/Desktop/2016lgb/";
        File root = new File(sourcePath);
        File[] files = root.listFiles();
        String filePath;

        Workbook workbook;
        Sheet sheet;
        Cell cell;
        String value;
        String tmp;
        String oldName;
        String newName;
        for (int i = 0; i < files.length; i++) {
            filePath = files[i].getPath();
            if (!filePath.endsWith(".xls")) {
                continue;
            }

            LOGGER.info(filePath);
            try {
                workbook = new HSSFWorkbook(new FileInputStream(filePath));
                sheet = workbook.getSheetAt(0);
                cell = sheet.getRow(1).getCell(0);
                value = cell.getStringCellValue();
                tmp = value;
                value = tmp.substring(0, tmp.indexOf("姓名：")) + tmp.substring(tmp.indexOf("性别："));
                LOGGER.info(value);
                cell.setCellValue(value);

                cell = sheet.getRow(2).getCell(0);
                value = cell.getStringCellValue();
                tmp = value;
                if (!" ".equals(value.substring(5, 6))) {
                    value = tmp.substring(0, 5) + "*  " + tmp.substring(tmp.indexOf("体检日期："));
                }
                LOGGER.info(value);
                cell.setCellValue(value);

                FileOutputStream outputStream = new FileOutputStream(filePath);
                workbook.write(outputStream);
                workbook.close();
                outputStream.flush();
                outputStream.close();

                oldName = filePath;
                newName = oldName.substring(0, oldName.indexOf("_") + 1) + i + ".xls";

                File oldFile = new File(oldName);
                File newFile = new File(newName);
                oldFile.renameTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void tianFangDa(String sourcePath, boolean classifyFlag, boolean indexOnly, boolean withRange) {
        try {
            String excelPath = sourcePath;
            if (excelPath.endsWith("/")) {
                excelPath = sourcePath.substring(0, sourcePath.length() - 1);
            }

            if (classifyFlag) {
                excelPath += "_classify.xlsx";
            } else {
                excelPath += ".xlsx";
            }
            if (indexOnly) {
                excelPath = sourcePath.substring(0, sourcePath.length() - 1) + "_index.xlsx";
            }

            try {
                new File(excelPath).delete();
            } catch (Exception e1) {
            }

            HashMap<String, Integer> allTitle = new HashMap<String, Integer>();
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("export data");
            Row titalRow = sheet.createRow(0);
            Row row = null;

            if (indexOnly) {
                titalRow.createCell(0).setCellValue("文件号");
                titalRow.createCell(1).setCellValue("部门科室");
                titalRow.createCell(2).setCellValue("项目套餐");
                titalRow.createCell(3).setCellValue("指标");
                titalRow.createCell(4).setCellValue("参考范围");
            } else {
                titalRow.createCell(0).setCellValue("文件号");
                titalRow.createCell(1).setCellValue("登记流水号");
                titalRow.createCell(2).setCellValue("姓名");
                titalRow.createCell(3).setCellValue("性别");
                titalRow.createCell(4).setCellValue("年龄");
                titalRow.createCell(5).setCellValue("单位名称");
                titalRow.createCell(6).setCellValue("体检日期");
                titalRow.createCell(7).setCellValue("收缩压");
                titalRow.createCell(8).setCellValue("舒张压");
            }

            List<File> files = FileOperationUtil.fileRecurses(sourcePath);
            Workbook wbr;
            Sheet sheetr;
            Row rowr;

            int titalColNum = 9;
            int rowNum = 1;
            String department = "";
            String project = "";
            String index;
            String key;
            String key2;
            String value;
            String range;
            String hint;
            String expand;
            Float dbp;
            Float sbp;
            Float valueF;
            Float rangeMax;
            Float rangeMin;
            Float tmp;
            String[] ranges;
            String[] infos;
            boolean dataFlag;
            boolean rowCreated;

            String indexFullName;
            HashSet<String> indexSet = new HashSet<String>();

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                dataFlag = false;
                if (!file.getPath().endsWith(".xls")) {
                    continue;
                }
                if (i % 1000 == 0) {
                    String o = i + "/";
                    o = "       ".substring(o.length()) + o;
                    LOGGER.info(o + files.size() + "  " + file.getPath());
                }
                try {
                    wbr = new HSSFWorkbook(new FileInputStream(file));
                } catch (Exception e1) {
                    LOGGER.warn(file.getPath());
                    continue;
                }
                sheetr = wbr.getSheetAt(0);
                rowCreated = false;
                for (int rowNumR = 0; rowNumR <= sheetr.getLastRowNum(); rowNumR++) {
                    rowr = sheetr.getRow(rowNumR);
                    String[] values = new String[5];
                    try {
                        values[0] = rowr.getCell(0).getStringCellValue().trim();
                        values[1] = rowr.getCell(1).getStringCellValue().trim();
                        values[2] = rowr.getCell(2).getStringCellValue().trim();
                        values[3] = rowr.getCell(3).getStringCellValue().trim();
                        values[4] = rowr.getCell(4).getStringCellValue().trim();
                    } catch (NullPointerException e) {
                        continue;
                    }

                    if (indexOnly) {
                        if (rowNumR < 3) {
                            continue;
                        }
                        if (values[0] != null && !values[0].contains("登记流水号") && !values[0].contains("单位名称") && "".equals(values[1]) && "".equals(values[2]) && "".equals(values[3])) {
                            department = values[0];
                        } else if (values[1].contains("检查日期")) {
                            project = values[0];
                        }

                        if ("项目名称".equals(values[0])) {
                            dataFlag = true;
                            continue;
                        } else if ("小  结：".equals(values[0])) {
                            dataFlag = false;
                            // department = "";
                            // project = "";
                        }

                        if (dataFlag) {
                            index = values[0];
                            indexFullName = department + project + index;
                            range = values[3];
                            if (indexSet.contains(indexFullName)) {
                                continue;
                            }
                            indexSet.add(indexFullName);

                            row = sheet.createRow(rowNum);
                            rowNum++;

                            row.createCell(0).setCellValue(file.getName());
                            row.createCell(1).setCellValue(department);
                            row.createCell(2).setCellValue(project);
                            row.createCell(3).setCellValue(index);
                            row.createCell(4).setCellValue(range);
                            if (rowNum == 1000001) {
                                rowNum = 1;
                                sheet = wb.createSheet("export data2");
                            }
                        }

                        continue;
                    }

                    if (values[1].contains("检查日期")) {
                        project = values[0];
                    }

                    if (values[0].contains("登记流水号")) {
                        infos = values[0].replaceAll(" ", "").replace("登记流水号", "").replace("姓名", "").replace("性别", "").replace("年龄", "").split("：");
                        row = sheet.createRow(rowNum);
                        rowNum++;
                        rowCreated = true;
                        try {
                            row.createCell(0).setCellValue(file.getName());
                            row.createCell(1).setCellValue(infos[1].trim());
                            row.createCell(2).setCellValue(infos[2].trim());
                            row.createCell(3).setCellValue(infos[3].trim());
                            row.createCell(4).setCellValue(infos[4].trim());
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    } else if (values[0].contains("一般情况") && !rowCreated) {
                        row = sheet.createRow(rowNum);
                        rowNum++;
                    }
                    if (values[0].contains("单位名称")) {
                        infos = values[0].replaceAll(" ", "").replace("单位名称", "").replace("体检日期", "").replace("/", "-").split("：");
                        try {
                            row.createCell(5).setCellValue(infos[1].trim());
                            row.createCell(6).setCellValue(infos[2].trim());
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    } else if ("项目名称".equals(values[0])) {
                        dataFlag = true;
                        continue;
                    } else if ("小  结：".equals(values[0])) {
                        dataFlag = false;

                        key = project + "&" + values[0];
                        value = values[1];
                        project = "";

                        if (!allTitle.containsKey(key) && !"".equals(value)) {
                            allTitle.put(key, titalColNum);
                            titalRow.createCell(titalColNum).setCellValue(key);
                            titalColNum++;
                        } else if ("".equals(value)) {
                            continue;
                        }

                        row.createCell(allTitle.get(key)).setCellValue(value);
                    }

                    if (dataFlag && row != null) {
                        key = project + "&" + values[0];
                        key2 = key + "-提示";
                        value = values[1];
                        range = values[3];
                        hint = values[4];
                        if (withRange) {
                            expand = "[" + (StringUtils.isNotBlank(range) ? range : "-") + "]" + values[2];
                        } else {
                            expand = "";
                        }
                        if ((project + "&" + "血压").equals(key) && value.contains("/")) {
                            ranges = value.split("/");
                            try {
                                sbp = Float.parseFloat(ranges[0].trim());
                                dbp = Float.parseFloat(ranges[1].trim());
                                if (sbp < dbp) {
                                    tmp = sbp;
                                    sbp = dbp;
                                    dbp = tmp;
                                }
                                row.createCell(7).setCellValue((int) Math.floor(sbp) + expand);
                                row.createCell(8).setCellValue((int) Math.floor(dbp) + expand);
                            } catch (NumberFormatException e) {
                            } catch (NullPointerException e) {
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            continue;
                        } else if (!allTitle.containsKey(key) && !"".equals(value)) {
                            allTitle.put(key, titalColNum);
                            titalRow.createCell(titalColNum).setCellValue(key);
                            titalColNum++;
                            // 添加提示列
                            allTitle.put(key2, titalColNum);
                            titalRow.createCell(titalColNum).setCellValue(key2);
                            titalColNum++;
                        } else if ("".equals(value)) {
                            continue;
                        }

                        try {
                            if (classifyFlag) {
                                if (!"-".equals(range) && (range.contains("--") || range.contains("-") || range.contains("～～") || range.contains("～"))) {
                                    range = range.replace("--", "-").replace("～～", "-").replace("～", "-");
                                    ranges = range.split("-");
                                    rangeMax = Float.parseFloat(ranges[0].trim());
                                    rangeMin = Float.parseFloat(ranges[1].trim());
                                    valueF = Float.parseFloat(value.replace("<", "").replace(">", "").trim());
                                    if (rangeMax < rangeMin) {
                                        tmp = rangeMax;
                                        rangeMax = rangeMin;
                                        rangeMin = tmp;
                                    }

                                    if (rangeMin == 0 && valueF < rangeMax) {
                                        value = "正常";
                                    } else if (rangeMin == 0 && valueF > rangeMax) {
                                        value = "异常";
                                    } else if (valueF < rangeMin) {
                                        value = "偏低";
                                    } else if (valueF > rangeMax) {
                                        value = "偏高";
                                    } else {
                                        value = "正常";
                                    }
                                } else if (range.contains("<")) {
                                    rangeMax = Float.parseFloat(range.replace("<", "").trim());
                                    valueF = Float.parseFloat(value.replace("<", "").replace(">", "").trim());
                                    if (valueF < rangeMax) {
                                        value = "正常";
                                    } else {
                                        value = "异常";
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {
                        } catch (NullPointerException e) {
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }

                        row.createCell(allTitle.get(key)).setCellValue(value + expand);
                        row.createCell(allTitle.get(key2)).setCellValue(hint);
                    }
                }
            }

            LOGGER.info("Title size: " + allTitle.size() + "  Save to file: " + excelPath);
            FileOutputStream outputStream = new FileOutputStream(excelPath);
            wb.write(outputStream);
            wb.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void overviewAnalysis(String inPath, String outPath) {
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
        File file = new File(inPath);
        if (file.exists() && !file.isDirectory()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String str, key;
                int value;
                String[] s1, s2;
                while ((str = br.readLine()) != null) {
                    s1 = str.replaceAll("： {2,9}", ";").replace("： ", ";").replace("[", "")
                            .replace("]", ";").replace(">", "").replace("<", "")
                            .replace("=", ".").replace("(", ".").replace(")", ".")
                            .replace("。", ".").split("、");
                    for (String cs1 : s1) {
                        s2 = cs1.replaceAll(" ", "").trim().split(";");
                        for (String cs2 : s2) {
                            if (cs2.contains("视力") || cs2.contains("C-UBI") || cs2.contains("STⅡⅢavFv") || cs2.contains("TV") || cs2.contains("Tv") || cs2.contains("avL") || cs2.contains("avl")) {
                                key = cs2.trim();
                            } else {
                                key = cs2.replaceAll("\\d+", "").replace(".", "").trim();
                            }

                            // if (":A提示感染".equals(key))
                            // LOGGER.info(str);

                            if (resultMap.keySet().contains(key) && !"+".equals(key) && !":".equals(key) && !"~".equals(key)) {
                                value = resultMap.get(key);
                                value++;
                                resultMap.put(key, value);
                            } else {
                                resultMap.put(key, 1);
                            }
                        }
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outPath, false));
            for (String key : resultMap.keySet()) {
                output.write(key + "\t" + resultMap.get(key) + "\n");
            }
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("end");
    }

    public static void whszxyy2load(String sourcePath) {
        List<File> files = FileOperationUtil.fileRecurses(sourcePath);
        for (int i = 0; i < files.size(); i++) {

        }
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public static void createContents(String path) {
        List<String> contentList = new ArrayList<String>();

        int i = 0;
        String[] fileName = new File(path).list();
        for (String name : fileName) {
            LOGGER.info(++i + "");
            if (name.endsWith(".xml")) {
                List<String> dataList = new ArrayList<String>();
                SAXReader reader = new SAXReader();
                File file = new File(path + name);
                Document document = null;
                try {
                    document = reader.read(file);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                Element root = document.getRootElement();
                for (Element ele1 : (List<Element>) root.elements()) {
                    for (Element ele2 : (List<Element>) ele1.elements()) {
                        StringBuilder tmpdata2 = new StringBuilder();
                        for (Attribute att3 : (List<Attribute>) ele2.attributes()) {
                            // System.out.print(att3.getValue() + ": ");
                            tmpdata2.append(att3.getValue()).append("#:#");
                        }
                        tmpdata2.append(ele2.getText().replaceAll("[\\t\\n\\r]", " "));
                        // dataList.add(tmpdata2.replaceAll("[\\t\\n\\r]", "
                        // "));
                        for (Element ele3 : (List<Element>) ele2.elements()) {
                            StringBuilder tmpdata3 = new StringBuilder();
                            // LOGGER.info();
                            for (Attribute att4 : (List<Attribute>) ele3.attributes()) {
                                // System.out.print(att4.getValue() + ":");
                                tmpdata3.append(att4.getValue()).append("#:#");
                            }
                            dataList.add(tmpdata3.toString().replaceAll("[\\t\\n\\r]", " "));
                            for (Element ele4 : (List<Element>) ele3.elements()) {
                                StringBuilder tmpdata4 = new StringBuilder();
                                // LOGGER.info();
                                for (Attribute att5 : (List<Attribute>) ele4.attributes()) {
                                    // System.out.print(att5.getValue() + ":");
                                    tmpdata4.append(att5.getValue()).append("#:#");
                                }
                                for (Element ele5 : (List<Element>) ele4.elements()) {
                                    // System.out.print(ele5.getText() + "|");
                                    tmpdata4.append(ele5.getText()).append("#,#");
                                }
                                // System.out.print(ele4.getText());
                                tmpdata4.append(ele4.getText());
                                dataList.add(tmpdata4.toString().replaceAll("[\\t\\n\\r]", " "));
                            }
                            // LOGGER.info();
                        }
                        // LOGGER.info(ele2.getText().replaceAll("[\\t\\n\\r]",
                        // " "));
                    }
                }
                String value;
                for (String data_tmp : dataList) {
                    String[] data_f = data_tmp.split("#:#");
                    if (data_f.length == 2 && !data_f[1].equals("")) {
                        String[] data_s = data_f[1].split("#,#");
                        if (!(data_s.length == 0) && !data_s[0].matches("^[0-9]+(.[0-9]{1,10})?$") && !data_f[0].equals("综述") && !data_f[0].equals("建议") && !data_f[0].equals("小结情况") && !data_f[0].equals("小结医生") && !data_f[0].equals("小结日期") && !data_f[0].equals("总检医生") && !data_f[0].equals("总检日期")) {
                            value = data_f[0] + "\t" + data_s[0];
                            // LOGGER.info(value + "\t" + name);
                            if (!findInList(value, contentList)) {
                                contentList.add(value);
                            }
                        }
                    }
                }
            }
        }
        File file = new File(path.substring(0, path.length() - 1) + ".txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String str_w : contentList) {
                writer.write(str_w + "\r\n");
            }
            writer.close();
        } catch (Exception e) {
        }
        LOGGER.info("finished.");
    }

    private static boolean findInList(String inStr, List<String> contentList) {
        for (String tmp : contentList) {
            if (tmp.equals(inStr)) {
                return true;
            }
        }
        return false;
    }

    private static String getMemoryInfo(long begin) {
        Runtime currRuntime = Runtime.getRuntime();
        String nFreeMemory = String.format("%.2f", (currRuntime.freeMemory() / 1024f / 1024));
        String nTotalMemory = String.format("%.2f", (currRuntime.totalMemory() / 1024f / 1024));
        return "Java infos: RunningTime = " + String.format("%.2f", (System.currentTimeMillis() - begin) / 1000f) + "S  FreeMemory = " + nFreeMemory + "M  TotalMemory = " + nTotalMemory + "M";
    }

    private static void copyFile(File oldFile, String newPath) {
        try {
            int byteRead;
            if (oldFile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldFile); // 读入原文件
                FileOutputStream foStream = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    foStream.write(buffer, 0, byteRead);
                }
                inStream.close();
                foStream.close();
            }
        } catch (Exception e) {
            LOGGER.info("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    private static String convert(String fullStr) {
        if (StringUtils.isBlank(fullStr)) {
            return "";
        }
        StringBuilder buf = new StringBuilder(fullStr.length());

        char[] c = fullStr.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= SBC_CHAR_START && c[i] <= SBC_CHAR_END) {
                buf.append((char) (c[i] - CONVERT_STEP));
            } else {
                buf.append(c[i]);
            }
        }
        return buf.toString();
    }
}
