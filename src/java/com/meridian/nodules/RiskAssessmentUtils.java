package com.meridian.nodules;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.meridian.nodules.model.NoduleData;

public class RiskAssessmentUtils {
    private static HashMap<Integer, Integer> assessmentResultMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, String> tiradsResultMap = new HashMap<Integer, String>();
    
    static {
        assessmentResultMap.put(1011, 3);
        assessmentResultMap.put(1012, 4);
        assessmentResultMap.put(1013, 5);
        assessmentResultMap.put(1021, 3);
        assessmentResultMap.put(1022, 5);
        assessmentResultMap.put(1023, 5);
        assessmentResultMap.put(2100, 2);
        assessmentResultMap.put(2211, 2);
        assessmentResultMap.put(2212, 3);
        assessmentResultMap.put(2213, 5);
        assessmentResultMap.put(2221, 2);
        assessmentResultMap.put(2222, 5);
        assessmentResultMap.put(2223, 5);
        assessmentResultMap.put(2311, 3);
        assessmentResultMap.put(2312, 4);
        assessmentResultMap.put(2313, 5);
        assessmentResultMap.put(2321, 3);
        assessmentResultMap.put(2322, 5);
        assessmentResultMap.put(2323, 5);
        assessmentResultMap.put(3030, 1);
        
        tiradsResultMap.put(1010, "4a");
        tiradsResultMap.put(1011, "4b");
        tiradsResultMap.put(1012, "4c");
        tiradsResultMap.put(1013, "4c");
        tiradsResultMap.put(1020, "4b");
        tiradsResultMap.put(1021, "4c");
        tiradsResultMap.put(1022, "4c");
        tiradsResultMap.put(1023, "5");
        tiradsResultMap.put(2100, "2");
        tiradsResultMap.put(2210, "3");
        tiradsResultMap.put(2211, "4a");
        tiradsResultMap.put(2212, "4b");
        tiradsResultMap.put(2213, "4c");
        tiradsResultMap.put(2220, "4a");
        tiradsResultMap.put(2221, "4b");
        tiradsResultMap.put(2222, "4c");
        tiradsResultMap.put(2223, "4c");
        tiradsResultMap.put(3030, "2");
    }
    
    public static int assessmentLogic(NoduleData noduleData) {
        return assessmentLogic(noduleData.getNoduleStructure(), noduleData.getNoduleSecondStructure(), noduleData.getNoduleHyperecho(), 
                noduleData.getNoduleInvasion(), noduleData.getNoduleEdge(), noduleData.getNoduleRatio(), noduleData.getNoduleCalcification(), "1");
    }
    
    /**
     * 
     * @author Reticence (liuyang_blue@qq.com)
     * @param structure :   结构
     * @param secondStructure :   二级结构
     * @param hyperecho : 回声
     * @param thyroid : 腺体外侵犯
     * @param edge :  边缘
     * @param aspectRatio :   纵横比
     * @param calcification : 钙化
     * @param noduleNum : 结节编号
     * @return -1: 无结节 |0:未知结果  |1:良性  |2:极低危  |3:低危  |4:中危  |5:高危
     */
    public static int assessmentLogic(String structure, String secondStructure, String hyperecho, String thyroid, String edge, String aspectRatio,
            String calcification, String noduleNum) {
        Integer value = -1;
        if (StringUtils.isNotBlank(noduleNum) && !"0".equals(noduleNum)) {
            int[] parameter = new int[4];
            parameter[0] = structureAssessment(structure);
            parameter[1] = secondStructureAssessment(secondStructure);
            parameter[2] = echoAssessment(hyperecho);
            parameter[3] = otherAssessment(thyroid, edge, aspectRatio, calcification);
            
            if (parameter[0] == 1) {
                parameter[1] = 0;
            } else if (parameter[0] == 2 && parameter[1] == 1) {
                parameter[2] = 0;
                parameter[3] = 0;
            } else if (parameter[0] == 3) {
                parameter[1] = 0;
                parameter[3] = 0;
            } 
            
            Integer key = parameter[0] * 1000 + parameter[1] * 100 + parameter[2] * 10 + parameter[3];
            value = assessmentResultMap.get(key);
            
            if (value == null) {
                value = 0;
            }
        }
        return value;
    }
    
    public static int suggestLogic(int assessmentResult, String noduleSize) {
        try {
            Float size = Float.parseFloat(noduleSize);
            if (assessmentResult == 1) {
                return 1; // 良性
            } else if (assessmentResult == 2 && size <= 1) {
                return 2; // 极低危：结节大小≤1cm
            } else if (assessmentResult == 2 && size > 1 && size <= 2) {
                return 3; // 极低危：结节大小＞1cm且≤2cm
            } else if (assessmentResult == 2 && size > 2) {
                return 4; // 极低危： 结节大小＞2cm
            } else if (assessmentResult == 3 && size < 0.5) {
                return 5; // 低危： 结节大小＜0.5cm
            } else if (assessmentResult == 3 && size >= 0.5 && size <= 1.5) {
                return 6; // 低危： 结节大小≥0.5cm且≤1.5cm
            } else if (assessmentResult == 3 && size > 1.5) {
                return 7; // 低危： 结节大小＞1.5cm
            } else if (assessmentResult == 4 && size < 0.5) {
                return 8; // 中危：结节大小＜0.5cm
            } else if (assessmentResult == 4 && size >= 0.5 && size <= 1) {
                return 9; // 中危：结节大小≥0.5cm且≤1cm
            } else if (assessmentResult == 4 && size > 1) {
                return 10; // 中危： 结节大小＞1cm
            } else if (assessmentResult == 5 && size <= 1) {
                return 11; // 高危： 结节大小≤1cm
            } else if (assessmentResult == 5 && size > 1) {
                return 12; // 高危： 结节大小＞1cm
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }
    
    public static String tiradsAssessmentLogic(NoduleData noduleData) {
        return tiradsAssessmentLogic(noduleData.getNoduleStructure(), noduleData.getNoduleSecondStructure(), noduleData.getNoduleHyperecho(), 
                noduleData.getNoduleEdge(), noduleData.getNoduleRatio(), noduleData.getNoduleCalcification(), "1");
    }
    
    /**
     * 
     * @author Reticence (liuyang_blue@qq.com)
     * @param structure :   结构
     * @param secondStructure :   二级结构
     * @param hyperecho : 回声
     * @param edge :  边缘
     * @param aspectRatio :   纵横比
     * @param calcification : 钙化
     * @param noduleNum : 结节编号
     * @return  Tirads评估结果
     */
    public static String tiradsAssessmentLogic(String structure, String secondStructure, String hyperecho, String edge, String aspectRatio, String calcification, String noduleNum) {
        String tiradsResult = "";
        if (StringUtils.isNotBlank(noduleNum) && !"0".equals(noduleNum)) {
            int[] parameter = new int[4];
            parameter[0] = structureAssessment(structure);
            parameter[1] = secondStructureAssessment(secondStructure);
            parameter[2] = echoAssessment(hyperecho);
            parameter[3] = otherAssessment(edge, aspectRatio, calcification);
            
            if (parameter[0] == 1) {
                parameter[1] = 0;
            } else if (parameter[0] == 2 && parameter[1] == 1) {
                parameter[2] = 0;
                parameter[3] = 0;
            } else if (parameter[0] == 2 && parameter[1] > 2) {
                parameter[1] = 2;
            } else if (parameter[0] == 3) {
                parameter[1] = 0;
                parameter[3] = 0;
            }
            
            Integer key = parameter[0] * 1000 + parameter[1] * 100 + parameter[2] * 10 + parameter[3];
            tiradsResult = tiradsResultMap.get(key);
            
            if (tiradsResult == null) {
                tiradsResult = "";
            }
        }
        return tiradsResult;
    }

    /**
     * 
     * @return 0:未知结果  |1:实性  |2:囊实性  |3:囊性
     */
    public static int structureAssessment(String structure) {
        if ("实性".equals(structure)) {
            return 1;
        } else if ("囊实性".equals(structure)) {
            return 2;
        } else if ("囊性".equals(structure)) {
            return 3;
        }
        return 0;
    }
    
    /**
     * 
     * @return 0:未知结果  |1:海绵征  |2:实性部分不偏心  |3:实性部分偏心
     */
    public static int secondStructureAssessment(String secondStructure) {
        if ("海绵征".equals(secondStructure)) {
            return 1;
        } else if ("实性部分不偏心".equals(secondStructure)) {
            return 2;
        } else if ("实性部分偏心".equals(secondStructure)) {
            return 3;
        }
        return 0;
    }
    
    /**
     * 
     * @return 0:未知结果  |1:高/等回声  |2:低回声  |3:无回声
     */
    public static int echoAssessment(String hyperecho) {
        if ("高回声".equals(hyperecho) || "等回声".equals(hyperecho)) {
            return 1;
        } else if ("低回声".equals(hyperecho)) {
            return 2;
        } else if ("无回声".equals(hyperecho)) {
            return 3;
        }
        return 0;
    }
    
    /**
     * 
     * @return 0:未知结果  |1:无腺体外侵犯  |2:边缘不规则or横纵比＞1or微钙化or不完整环状钙化且钙化外周有低回声成分  |3:腺体外侵犯
     */
    public static int otherAssessment(String thyroid, String edge, String aspectRatio, String calcification) {
        if ("无腺体外侵犯".equals(thyroid)) {
            if ("边缘不规则".equals(edge)) {
                return 2;
            } else if ("＞1".equals(aspectRatio) || "≥1".equals(aspectRatio)) { 
                return 2;
            } else if ("微钙化".equals(calcification) || "不完整环状钙化且钙化外周有低回声成分".equals(calcification)) {
                return 2;
            } else {
                return 1;
            }
        } else if ("有腺体外侵犯".equals(thyroid)) {
            return 3;
        }
        return 0;
    }
    
    public static int otherAssessment(String edge, String aspectRatio, String calcification) {
        int result = 0;
        if ("边缘不规则".equals(edge)) {
            result++;
        }
        if ("≥1".equals(aspectRatio)) {
            result++;
        }
        if ("微钙化".equals(calcification)) {
            result++;
        }
        return result;
    }
}
