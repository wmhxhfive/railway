package com.railway.wm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.railway.wm.request.TrainInfoReq;
import com.railway.wm.response.TrainInfo4ScreenRep;
import com.railway.wm.response.TrainInfoRps;
import com.railway.wm.service.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Controller
@RequestMapping("/trainInfo")
public class TrainInfoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 大屏展示接口
     */
    @Resource
    TrainService trainService;
    @RequestMapping(value="/screen", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public TrainInfo4ScreenRep getScreenInfo( @RequestParam(value="railStation",required=true) String railStation ){
        log.info("getScreenInfo,request{}",JSONObject.toJSON(railStation));
        TrainInfo4ScreenRep trainInfo4ScreenRep=trainService.findTrainInfoByStation(railStation);
        log.info("getScreenInfo,response{}",JSONObject.toJSON(trainInfo4ScreenRep));
        return trainInfo4ScreenRep;
    }

    /**
     * 机车概要信息获取
     * @param trainInfoReq
     * @return
     */
    @RequestMapping(value="/list",method = RequestMethod.POST)
    @ResponseBody
    public TrainInfoRps getTrainInfos( @RequestBody TrainInfoReq trainInfoReq ){
        log.info("getTrainInfos,request{}",JSONObject.toJSON(trainInfoReq));
        TrainInfoRps trainInfoRps=trainService.findTrainInfoByCondition(trainInfoReq);
        log.info("getTrainInfos,response{}",JSONObject.toJSON(trainInfoRps));
        return trainInfoRps;
    }

    /**
     * 机车详细信息获取
     * @param id
     * @return
     */
    @RequestMapping(value="/detail" ,method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public TrainInfo4ScreenRep getTrainDetailInfos(@RequestParam(value="id",required=true)String id ){
        log.info("getTrainInfos,request{}",id);
        TrainInfo4ScreenRep trainInfoRps=trainService.findTrainDetailById(id);
        log.info("getTrainInfos,response{}",JSONObject.toJSON(trainInfoRps));
        return trainInfoRps;
    }

}
