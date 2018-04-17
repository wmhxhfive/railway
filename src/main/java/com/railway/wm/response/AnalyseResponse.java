package com.railway.wm.response;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by WANGMING602 on 2018/4/10.
 */
public class AnalyseResponse implements Serializable {
    private String railNo;  // 车牌号码
    private String partNo; // 部位标号
    private String url;// 分析照片存储的位置
    private Date createDate; // 创建时间
    private Integer analyResult;
    private String checkDate; // 机车检测时间
    private String railStation;//检测车站
    private String no;//当天机车序号

    public String getRailNo() {
        return railNo;
    }

    public void setRailNo(String railNo) {
        this.railNo = railNo;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getRailStation() {
        return railStation;
    }

    public void setRailStation(String railStation) {
        this.railStation = railStation;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
