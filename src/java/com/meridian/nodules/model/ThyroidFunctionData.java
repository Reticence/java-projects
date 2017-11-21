package com.meridian.nodules.model;

/**
 * @author 刘洋
 * @date 创建时间：2016年7月19日 下午3:08:15
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class ThyroidFunctionData {
    private String indexName;
    private String indexValue;
    private String indexUnit;
    private String indexRange;
    private int indexNormalFlag;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    public String getIndexUnit() {
        return indexUnit;
    }

    public void setIndexUnit(String indexUnit) {
        this.indexUnit = indexUnit;
    }

    public String getIndexRange() {
        return indexRange;
    }

    public void setIndexRange(String indexRange) {
        this.indexRange = indexRange;
    }

    public int getIndexNormalFlag() {
        return indexNormalFlag;
    }

    public void setIndexNormalFlag(int indexNormalFlag) {
        this.indexNormalFlag = indexNormalFlag;
    }

}
