package com.meridian.nodules.model;

import java.util.List;

/**
 * @author 刘洋
 * @date 创建时间：2016年7月19日 下午3:06:11
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class ThyroidScreeningData {
    private boolean thyroidDataIntegrityFlag;
    private EvaluateResultData evaluateResultData;
    private ThyroidNodulesData thyroidNodulesData;
    private List<ThyroidFunctionData> thyroidFunctionData;
    private List<String> imgUrl;
    
    public boolean isThyroidDataIntegrityFlag() {
        return thyroidDataIntegrityFlag;
    }

    public void setThyroidDataIntegrityFlag(boolean thyroidDataIntegrityFlag) {
        this.thyroidDataIntegrityFlag = thyroidDataIntegrityFlag;
    }

    public EvaluateResultData getEvaluateResultData() {
        return evaluateResultData;
    }

    public void setEvaluateResultData(EvaluateResultData evaluateResultData) {
        this.evaluateResultData = evaluateResultData;
    }

    public ThyroidNodulesData getThyroidNodulesData() {
        return thyroidNodulesData;
    }

    public void setThyroidNodulesData(ThyroidNodulesData thyroidNodulesData) {
        this.thyroidNodulesData = thyroidNodulesData;
    }

    public List<ThyroidFunctionData> getThyroidFunctionData() {
        return thyroidFunctionData;
    }

    public void setThyroidFunctionData(List<ThyroidFunctionData> thyroidFunctionData) {
        this.thyroidFunctionData = thyroidFunctionData;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

}
