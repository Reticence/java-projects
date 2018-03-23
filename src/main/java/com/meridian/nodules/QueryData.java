package com.meridian.nodules;

import java.util.List;

import com.meridian.nodules.model.EvaluateResultData;
import com.meridian.nodules.model.NoNoduleData;
import com.meridian.nodules.model.NoduleData;
import com.meridian.nodules.model.StaticVariable;
import com.meridian.nodules.model.ThyroidFunctionData;
import com.meridian.nodules.model.ThyroidScreeningData;

/**
 * @author 刘洋
 * @date 2016年7月15日 下午2:50:09
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class QueryData {
    
    private ThyroidScreeningData tsd;

    private EvaluateResultData evaluateResultData = new EvaluateResultData();

    /** 结论 **/
    private String thyroidConclusion;
    /** 甲状腺无结节数据标志 **/
    private boolean thyroidNoNodulesDataFlag = false;
    /** 甲状腺结节数据标志 **/
    private boolean thyroidNodulesDataFlag = false;
    /** 甲状腺功能数据标志 **/
    private boolean thyroidFunctionDataFlag = false;

    public QueryData(ThyroidScreeningData tsd) {
        this.tsd = tsd;
    }

    public void queryData() {
        tsd.setEvaluateResultData(evaluateResultData);

        thyroidFunctionDataFlag = null != tsd.getThyroidFunctionData();
        thyroidFunctionAssessment();
        thyroidNodulesDataFlag = null != tsd.getThyroidNodulesData().getNoduleDataList() && 0 < tsd.getThyroidNodulesData().getNoduleDataList().size();
        if (!thyroidNodulesDataFlag) {
            NoNoduleData noNoduleData = tsd.getThyroidNodulesData().getNoNoduleData();
            String suggest = noNodulesAssessment(noNoduleData.getThyroidBodyEcho(), noNoduleData.getThyroidBodyDistribution());
            noNoduleData.setThyroidBodySuggest(suggest);
        } else {
            List<NoduleData> noduleDataList = tsd.getThyroidNodulesData().getNoduleDataList();
            for (NoduleData noduleData : noduleDataList) {
                int readColumn;
                if (tsd.getThyroidNodulesData().getNodulesCount() == 1) {
                    readColumn = 0;
                } else {
                    readColumn = 4;
                }
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {

                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    readColumn = readColumn + 1;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    readColumn = readColumn + 3;
                } else {
                    readColumn = readColumn + 2;
                }
                noduleData.setNoduleSuggest(StaticVariable.NODULE_SUGGESTS[Integer.parseInt(noduleData.getNoduleSuggest())][readColumn].replace("${disease}", thyroidConclusion));
            }
        }
        
        if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
            evaluateResultData.setThyroidFunctionResult(StaticVariable.THYROID_FUNCTION_NORMAL);
        } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL || thyroidConclusion == StaticVariable.THYROID_FUNCTION_LOW
                || thyroidConclusion == StaticVariable.THYROID_FUNCTION_SUBCLINICAL || thyroidConclusion == StaticVariable.THYROID_FUNCTION_HYPERTHYROIDISM
                || thyroidConclusion == StaticVariable.THYROID_FUNCTION_AUTOIMMUNE_DISEASE) {
            evaluateResultData.setThyroidFunctionResult(StaticVariable.THYROID_FUNCTION_ABNORMAL);
        }

        tsd.setThyroidDataIntegrityFlag(thyroidNodulesDataFlag || thyroidNoNodulesDataFlag);
    }

    /**
     * 甲功评估
     * 
     * @author 刘洋
     * @date 2016年10月18日
     * @version 1.0
     */
    private void thyroidFunctionAssessment() {
        if (!thyroidFunctionDataFlag) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_NODATA;
            return;
        }
        
        int conclusion = 0;
        int[] conclusions = { 0, 0, 0, 0, 0, }; // 甲状腺功能低下, 亚临床甲减, 甲状腺功能亢进,
                                                // 甲状腺功能亢进, 自身免疫性疾病
        int[] indexs = new int[6]; // T4, FT4, TSH, FT3, A_TG, A_TPO,
        int thyroidConclusionFlag = 0;
        String indexName;
        int indexNormalFlag;
        int indexNormalFlagAbs = 0;
        for (ThyroidFunctionData data : tsd.getThyroidFunctionData()) {
            indexName = data.getIndexName();
            indexNormalFlag = data.getIndexNormalFlag();
            if ("T4".equals(indexName)) {
                indexs[0] = indexNormalFlag;
            } else if ("FT4".equals(indexName)) {
                indexs[1] = indexNormalFlag;
            } else if ("TSH".equals(indexName)) {
                indexs[2] = indexNormalFlag;
            } else if ("FT3".equals(indexName)) {
                indexs[3] = indexNormalFlag;
            } else if ("A_TG".equals(indexName)) {
                indexs[4] = indexNormalFlag;
            } else if ("A_TPO".equals(indexName)) {
                indexs[5] = indexNormalFlag;
            }

            indexNormalFlagAbs = Math.abs(indexNormalFlag);
            if (indexNormalFlagAbs == 1) {
                thyroidConclusionFlag++;
            }
        }
        if (indexs[0] == -1) {
            conclusions[0] = 1;
        }
        if (indexs[1] == 0 && indexs[2] == 1) {
            conclusions[1] = 1;
        }
        if ((indexs[2] != 0 && indexs[3] == 1) || (indexs[2] != 0 && indexs[1] == 1)) {
            conclusions[2] = 1;
        }
        if (indexs[2] != 0 && indexs[3] == 1 && indexs[1] == 1) {
            conclusions[3] = 1;
        }
        if ((indexs[4] != 0 || indexs[5] != 0)) {
            conclusions[4] = 1;
        }

        int hyperthyroidism = 0;
        if (conclusions[2] == 1 || conclusions[3] == 1) {
            hyperthyroidism = 1;
        }

        conclusion = conclusions[0] + conclusions[1] + hyperthyroidism + conclusions[4];

        if (conclusion > 1) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_ABNORMAL;
        } else if (conclusions[0] == 1 && thyroidConclusionFlag == 1) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_LOW;
        } else if (conclusions[1] == 1 && thyroidConclusionFlag == 1) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_SUBCLINICAL;
        } else if (conclusions[2] == 1 && thyroidConclusionFlag == 2) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_HYPERTHYROIDISM;
        } else if (conclusions[3] == 1 && thyroidConclusionFlag == 3) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_HYPERTHYROIDISM;
        } else if (conclusions[4] == 1 && thyroidConclusionFlag == 1) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_AUTOIMMUNE_DISEASE;
        } else if (thyroidConclusionFlag > 0) {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_ABNORMAL;
        } else {
            thyroidConclusion = StaticVariable.THYROID_FUNCTION_NORMAL;
        }
    }

    /**
     * 无结节评估
     * 
     * @author 刘洋
     * @date 2016年10月18日
     * @version 1.0
     * @param thyroidBodyEcho
     * @param thyroidBodyDistribution
     * @return
     */
    private String noNodulesAssessment(String thyroidBodyEcho, String thyroidBodyDistribution) {
        if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_LOW || thyroidConclusion == StaticVariable.THYROID_FUNCTION_SUBCLINICAL
                || thyroidConclusion == StaticVariable.THYROID_FUNCTION_HYPERTHYROIDISM
                || thyroidConclusion == StaticVariable.THYROID_FUNCTION_AUTOIMMUNE_DISEASE) {
            return StaticVariable.THYROID_FUNCTION_DISEASE.replace("${disease}", thyroidConclusion);
        }
        String result = null;
        if ("中等".equals(thyroidBodyEcho)) {
            if ("均匀".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
//                    thyroidNoNodulesDataFlag = true;
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_5;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
//                    thyroidNoNodulesDataFlag = true;
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_2;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
//                    thyroidNoNodulesDataFlag = true;
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_1;
                }
            } else if ("不均匀".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            } else if ("片状".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            }
        } else if (("减低（降低）".equals(thyroidBodyEcho) || "减低".equals(thyroidBodyEcho) || "降低".equals(thyroidBodyEcho))) {
            if ("均匀".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            } else if ("不均匀".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            } else if ("片状".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            }
        } else if ("增高".equals(thyroidBodyEcho)) {
            if ("均匀".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            } else if ("不均匀".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            } else if ("片状".equals(thyroidBodyDistribution)) {
                if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NODATA) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_6;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_NORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_4;
                } else if (thyroidConclusion == StaticVariable.THYROID_FUNCTION_ABNORMAL) {
                    result = StaticVariable.THYROID_FUNCTION_ASSESSMENT_3;
                }
            }
        }
        if (null != result) {
            thyroidNoNodulesDataFlag = true;
        }
        return result;
    }
}
