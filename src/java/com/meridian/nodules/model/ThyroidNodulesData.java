package com.meridian.nodules.model;

import java.util.List;

/**
 * @author 刘洋
 * @date 创建时间：2016年7月19日 下午3:07:16
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class ThyroidNodulesData {
    private int nodulesCount;
    private NoNoduleData noNoduleData;
    private List<NoduleData> noduleDataList;

    public int getNodulesCount() {
        return nodulesCount;
    }

    public void setNodulesCount(int nodulesCount) {
        this.nodulesCount = nodulesCount;
    }

    public NoNoduleData getNoNoduleData() {
        return noNoduleData;
    }

    public void setNoNoduleData(NoNoduleData noNoduleData) {
        this.noNoduleData = noNoduleData;
    }

    public List<NoduleData> getNoduleDataList() {
        return noduleDataList;
    }

    public void setNoduleDataList(List<NoduleData> noduleDataList) {
        this.noduleDataList = noduleDataList;
    }

}
