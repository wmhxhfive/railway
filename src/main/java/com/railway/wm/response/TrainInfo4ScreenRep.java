package com.railway.wm.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrainInfo4ScreenRep extends BaseResponse implements Serializable {
    List<TrainInfo> trainInfos =new ArrayList<>();
    List<TrainDetailInfo> trainDetailInfos=new ArrayList<>();

    public List<TrainInfo> getTrainInfos() {
        return trainInfos;
    }

    public void setTrainInfos(List<TrainInfo> trainInfos) {
        this.trainInfos = trainInfos;
    }

    public List<TrainDetailInfo> getTrainDetailInfos() {
        return trainDetailInfos;
    }

    public void setTrainDetailInfos(List<TrainDetailInfo> trainDetailInfos) {
        this.trainDetailInfos = trainDetailInfos;
    }
}
