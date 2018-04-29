package com.railway.wm.service;

import com.railway.wm.request.TrainInfoReq;
import com.railway.wm.response.TrainInfo;
import com.railway.wm.response.TrainInfo4ScreenRep;
import com.railway.wm.response.TrainInfoRps;

import java.util.List;

public interface TrainService {


    TrainInfo4ScreenRep findTrainInfoByStation(String railStation);

    TrainInfoRps findTrainInfoByCondition(TrainInfoReq trainInfo);

    TrainInfo4ScreenRep findTrainDetailById(String id);


}
