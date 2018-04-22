package com.railway.wm;

import com.railway.wm.dao.AnalyseRepository;
import com.railway.wm.dao.TrainInfoRepository;
import com.railway.wm.dao.UserRepository;
import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.domain.TrainInfoDao;
import com.railway.wm.domain.User;
import com.railway.wm.request.TrainInfoReq;
import com.railway.wm.response.TrainInfo;
import com.railway.wm.response.TrainInfo4ScreenRep;
import com.railway.wm.response.TrainInfoRps;
import com.railway.wm.service.FileProgressService;
import com.railway.wm.service.TrainService;
import jdk.internal.instrumentation.TypeMapping;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WmApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	FileProgressService  fileProgressService;

	@Autowired
	TrainInfoRepository trainInfoRepository;

	@Autowired
	AnalyseRepository analyseRepository;

	@Autowired
	private TrainService trainService;

	@Test
	public void contextLoads() {
		User user=new User();
		user.setMoblie("15618409916");
		user.setName("wm");
		userRepository.save(user);
		System.out.println("________________"+userRepository.findAll().size()+"");
	}
	@Test
	public void file(){
		try {
			fileProgressService.scanFileDirectAndResultIntoDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void  sqlTest(){
		List<AnalyseResult> results=analyseRepository.findLatelyCheckResult();
		System.out.printf(results.size()+"+++++++++++++++++");
		AnalyseResult analyseResult=new AnalyseResult();
		//analyseResult.setRailStation("hf");

	}

	@Test
	public void top(){
		List<TrainInfoDao> list=trainInfoRepository.findTop5ByRailStationOrderByCheckDateDesc("hf");
		System.out.printf(list.stream().toString());
		TrainInfoDao infoDao=list.get(0);
		List<AnalyseResult> analyseResults=analyseRepository.findAnalyseResultByTrainInfoIdAndCheckDate(infoDao.getId(),infoDao.getCheckDate());
		System.out.printf("---"+analyseResults.size());
	}


	@Test
	public void tailServer(){

		TrainInfo4ScreenRep trainInfo4ScreenRep=trainService.findTrainInfoByStation("hf");


		System.out.printf("返回报文");

		System.out.printf("--"+trainInfo4ScreenRep.getRet());
	}


	@Test
	public  void findBycondition(){
		TrainInfoReq trainInfo=new TrainInfoReq();
		trainInfo.setRailNo("hf");
		trainInfo.setIsNormal("0");
		trainInfo.setPage(0);
		trainInfo.setPageSize(2);
		TrainInfoRps rps=trainService.findTrainInfoByCondition(trainInfo);
		System.out.printf("--"+ rps);
	}
}
