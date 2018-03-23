package com.meridian.nodules.model;
/** 
* @author 刘洋
* @date 创建时间：2016年7月18日 下午5:19:36 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class StaticVariable {
    
    public final static String THYROID_FUNCTION_NODATA = "没有甲状腺功能检查";
    public final static String THYROID_FUNCTION_NORMAL = "甲状腺功能正常";
    public final static String THYROID_FUNCTION_ABNORMAL = "甲状腺功能异常";
    public final static String THYROID_FUNCTION_LOW = "甲状腺功能低下";
    public final static String THYROID_FUNCTION_SUBCLINICAL = "亚临床甲减";
    public final static String THYROID_FUNCTION_HYPERTHYROIDISM = "甲状腺功能亢进";
    public final static String THYROID_FUNCTION_AUTOIMMUNE_DISEASE = "自身免疫性疾病";

    public final static String THYROID_FUNCTION_ASSESSMENT_1 = "根据您的体检结果，目前未发现甲状腺结节，但您的甲状腺功能存在异常，建议您到专科就诊。";
    public final static String THYROID_FUNCTION_ASSESSMENT_2 = "恭喜您，根据您的体检结果，目前未发现甲状腺结节，且甲状腺功能正常，无需进行进一步复查。";
    public final static String THYROID_FUNCTION_ASSESSMENT_3 = "根据您的体检结果，目前未发现甲状腺结节，但您的甲状腺功能存在异常，诊断提示甲状腺弥漫性病变，建议您到专科就诊。";
    public final static String THYROID_FUNCTION_ASSESSMENT_4 = "根据您的体检结果，虽然未发现甲状腺结节，但诊断提示甲状腺弥漫性病变，建议您到专科就诊。";
    public final static String THYROID_FUNCTION_ASSESSMENT_5 = "恭喜您，根据您的体检结果，目前未发现甲状腺结节，无需进行进一步复查。";
    public final static String THYROID_FUNCTION_ASSESSMENT_6 = "根据您的体检结果，虽然未发现甲状腺结节，但诊断提示甲状腺弥漫性病变，建议行甲状腺功能检查并到专科就诊。";
    public final static String THYROID_FUNCTION_DISEASE = "根据您的体检结果，目前未发现甲状腺结节，但您目前存在${disease}的风险，建议您到专科就诊";
    
    public final static String EVALUATE_RESULT_SINGLE = "甲状腺单发结节";
    public final static String EVALUATE_RESULT_MULTIPLE = "甲状腺多发结节";
    public final static String EVALUATE_RESULT_NONODULES = "甲状腺未见结节";
    public final static String EVALUATE_RESULT_DISEASE = "甲状腺弥漫性病变";
    
    public final static String[][] NODULE_SUGGESTS = {
            {
                "", "", "", "", "", "", "", "",
            },
            {
                "您的甲状腺结节良性可能性大，您无需进行甲状腺超声复查。",
                "您的甲状腺结节为恶性的可能性很小，但由于您的甲状腺功能存在异常，建议您到专科就诊。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议您到专科就诊。",
                "您的甲状腺结节良性可能性大，无需进行甲状腺超声复查，但建议行甲状腺功能检查。",
                "您的甲状腺结节良性可能性大，您无需进行甲状腺超声复查。",
                "您的甲状腺结节良性可能性大，但甲状腺功能存在异常，建议您到专科就诊。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议您到专科就诊。",
                "您的甲状腺结节良性可能性大，无需进行甲状腺超声复查，但建议行甲状腺功能检查。",
            },
            {
                "您的甲状腺结节良性可能性大，您无需进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议您到专科就诊。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议您到专科就诊。",
                "您的甲状腺结节良性可能性大，无需进行甲状腺超声复查，但建议行甲状腺功能检查。",
                "您的甲状腺结节良性可能性大，您无需进行甲状腺超声复查。",
                "您的甲状腺结节良性可能性大，但甲状腺功能存在异常，建议您到专科就诊。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议您到专科就诊。",
                "您的甲状腺结节良性可能性大，无需进行甲状腺超声复查，但建议行甲状腺功能检查。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查并专科就诊，同时在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较低，建议您到专科就诊。并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较低，但您的甲状腺功能存在异常，建议您到专科就诊。并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较低，但存在${disease}的风险，建议您到专科就诊。并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较低，建议行甲状腺功能检查并到专科就诊，同时在12-24个月后进行甲状腺超声复查。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查，并到专科就诊，同时在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，建议您到专科就诊，并在12-24个月后进行甲状腺超声复查，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，加之您的甲状腺功能检查异常，建议您到专科就诊，并在12-24个月后进行甲状腺超声复查，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，并存在${disease}的风险，建议您到专科就诊，并在12-24个月后进行甲状腺超声复查，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，建议行甲状腺功能检查，并到专科就诊，同时在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议您到专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查并专科就诊，同时在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节较小，风险评估较低，建议您到专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节较小，但您的甲状腺功能检查异常，建议您到专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节较小，但存在${disease}的风险，建议您到专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节较小，风险评估较低，建议行甲状腺功能检查并到专科就诊，同时在24个月后进行甲状腺超声复查。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查并专科就诊，同时在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节评估结果为低危，建议您到专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节评估结果为低危，但您的甲状腺功能检查异常，建议您到专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节评估结果为低危，且存在${disease}的风险，建议专科就诊，并在12-24个月后进行甲状腺超声复查。",
                "您的甲状腺结节评估结果为低危，建议行甲状腺功能检查并到专科就诊，同时在12-24个月后进行甲状腺超声复查。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查，并到专科就诊，同时在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，建议您到专科就诊，并在6-12个月后进行甲状腺超声复查，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，加之您的甲状腺功能检查异常，建议您到专科就诊，并在6-12个月后进行甲状腺超声复查，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，并存在${disease}的风险，建议您到专科就诊，并在6-12个月后进行甲状腺超声复查，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节较大，建议行甲状腺功能检查，并到专科就诊，同时在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
            },
            {
                "您的甲状腺超声检查发现结节，建议在24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议您到专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议您到专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查，同时在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较高，建议您到专科就诊，同时建议在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较高，并存在甲状腺功能检查异常，建议您到专科就诊，同时在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较高，且存在${disease}的风险，建议您到专科就诊，并在24个月后进行甲状腺超声复查。",
                "您的甲状腺结节风险分层较高，建议行甲状腺功能检查，并到专科就诊，同时在24个月后进行甲状腺超声复查。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查，并到专科就诊，同时在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在甲状腺功能异常，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在${disease}的风险，建议专科就诊，并在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，建议行甲状腺功能检查，并到专科就诊，同时在12-24个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现存在结节，且存在${disease}的风险，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）和甲状腺功能检查。",
                "您的甲状腺结节风险分层较高，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在甲状腺功能异常，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在${disease}的风险，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，建议行甲状腺功能检查，并到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
            },
            {
                "您的甲状腺超声检查发现结节，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，建议行甲状腺功能检查，并到专科就诊，同时在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在甲状腺功能异常，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在${disease}的风险，建议专科就诊，并在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，建议行甲状腺功能检查，并到专科就诊，同时在6-12个月后进行甲状腺超声复查。必要时行甲状腺细针穿刺活检（FNA）。",
            },
            {
                "您的甲状腺超声检查发现结节，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）进一步明确。",
                "您的甲状腺超声检查发现结节，且甲状腺功能存在异常，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，且存在${disease}的风险，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺超声检查发现结节，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）和甲状腺功能检查进一步明确。",
                "您的甲状腺结节风险分层较高，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在甲状腺功能异常，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，并存在${disease}的风险，建议您到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
                "您的甲状腺结节风险分层较高，建议行甲状腺功能检查，并到专科就诊，必要时行甲状腺细针穿刺活检（FNA）。",
            },
    };
    
}