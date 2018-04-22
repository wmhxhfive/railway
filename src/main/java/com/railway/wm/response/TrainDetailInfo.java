package com.railway.wm.response;

import java.io.Serializable;

public class TrainDetailInfo implements Serializable {

    private Long trainInfoId; //机车关联id
    private String partNo; // 部位标号
    private String url;// 分析照片存储的位置
    private Integer analyResult;
    private String checkDate; // 机车检测时间

    public Long getTrainInfoId() {
        return trainInfoId;
    }

    public void setTrainInfoId(Long trainInfoId) {
        this.trainInfoId = trainInfoId;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAnalyResult() {
        return analyResult;
    }

    public void setAnalyResult(Integer analyResult) {
        this.analyResult = analyResult;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
}
