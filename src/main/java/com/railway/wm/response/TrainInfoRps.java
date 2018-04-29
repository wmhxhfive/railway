package com.railway.wm.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrainInfoRps extends BaseResponse implements Serializable {
    List<TrainInfo> trainInfoList=new ArrayList<TrainInfo>();
    long totalNum;
    int totalPage;

    public List<TrainInfo> getTrainInfoList() {
        return trainInfoList;
    }

    public void setTrainInfoList(List<TrainInfo> trainInfoList) {
        this.trainInfoList = trainInfoList;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

}
