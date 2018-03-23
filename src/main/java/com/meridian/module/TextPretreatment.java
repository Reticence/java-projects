package com.meridian.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 刘洋
 * @date 2017年3月7日 下午2:37:58
 * @version 1.0
 * @parameter
 */
public class TextPretreatment {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextPretreatment.class);
    
    private static List<String[]> generalRules = new ArrayList<String[]>();
    private static Map<String, List<String[]>> specificRules = new HashMap<String, List<String[]>>();
    
    public static void setGeneralRules(List<String[]> generalRules) {
        TextPretreatment.generalRules = generalRules;
    }

    public static void setSpecificRules(Map<String, List<String[]>> specificRules) {
        TextPretreatment.specificRules = specificRules;
    }

    public static String generalRulesHandler(String str) {
        return execRules(str, generalRules);
    }
    
    public static String specificRulesHandler(String str, String indexCode) {
        List<String[]> rulesList = specificRules.get(indexCode);
        if (null != rulesList && rulesList.size() > 0) {
            str = execRules(str, rulesList);
        }
        return str;
    }
    
    private static String execRules(String str, List<String[]> rulesList) {
        for (int i = 0; i < rulesList.size(); i++) {
            String[] rulesValue = rulesList.get(i);
            try {
                str = str.replace(rulesValue[0], rulesValue[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                StringBuffer errorMsg = new StringBuffer("Wrongful rules: ");
                for (String string : rulesValue) {
                    errorMsg.append(" >> " + string);
                }
                LOGGER.error(errorMsg.toString());
            }
        }
        return str;
    }
}
