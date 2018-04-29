package com.railway.wm.controller;

import com.railway.wm.request.TrainInfoReq;
import com.railway.wm.response.TrainInfo4ScreenRep;
import com.railway.wm.response.TrainInfoRps;
import com.railway.wm.service.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;


@Controller
@RequestMapping("/trainInfo")
public class TrainInfoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Resource
    TrainService trainService;
    @RequestMapping(value="/screen")
    @ResponseBody
    public TrainInfo4ScreenRep getScreenInfo( @Valid String railStation ){
        log.info("getScreenInfo,request{}",railStation);
        TrainInfo4ScreenRep trainInfo4ScreenRep=trainService.findTrainInfoByStation(railStation);
        log.info("getScreenInfo,response{}",trainInfo4ScreenRep);
        return trainInfo4ScreenRep;
    }

    @RequestMapping(value="/list")
    @ResponseBody
    public TrainInfoRps getTrainInfos( @Valid TrainInfoReq trainInfoReq ){
        log.info("getTrainInfos,request{}",trainInfoReq);
        TrainInfoRps trainInfoRps=trainService.findTrainInfoByCondition(trainInfoReq);
        log.info("getTrainInfos,response{}",trainInfoRps);
        return trainInfoRps;
    }


    @RequestMapping(value="/detail")
    @ResponseBody
    public TrainInfo4ScreenRep getTrainDetailInfos( String id ){
        log.info("getTrainInfos,request{}",id);
        TrainInfo4ScreenRep trainInfoRps=trainService.findTrainDetailById(id);
        log.info("getTrainInfos,response{}",trainInfoRps);
        return trainInfoRps;
    }





}
