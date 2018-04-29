package com.railway.wm.request;

import com.alibaba.fastjson.JSONObject;
import com.railway.wm.response.BaseResponse;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TrainInfoReq extends BaseResponse implements Serializable {
    private String railNo;  //  机车编号
    private String isNormal; // 是否正常
    private String beginCheckDate; // 机车检测时间
    private String endCheckDate; // 机车检测时间
    private String railStation;//检测车站
    @NotNull(message = "page 不允许为空")
    private  int page; //第几页
    @NotNull(message = "页面大小需要指定")
    private int pageSize;//页面大小


    public String getRailNo() {
        return railNo;
    }

    public void setRailNo(String railNo) {
        this.railNo = railNo;
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal;
    }

    public String getBeginCheckDate() {
        return beginCheckDate;
    }

    public void setBeginCheckDate(String beginCheckDate) {
        this.beginCheckDate = beginCheckDate;
    }

    public String getEndCheckDate() {
        return endCheckDate;
    }

    public void setEndCheckDate(String endCheckDate) {
        this.endCheckDate = endCheckDate;
    }

    public String getRailStation() {
        return railStation;
    }

    public void setRailStation(String railStation) {
        this.railStation = railStation;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public static void main(String[] args) {
        TrainInfoReq req=new TrainInfoReq();
        req.setPageSize(10);
        req.setPage(0);
        req.setRailStation("hf");
        System.out.printf(JSONObject.toJSONString(req));
    }
}
