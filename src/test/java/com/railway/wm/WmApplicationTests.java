package com.railway.wm;

import com.railway.wm.dao.AnalyseRepository;
import com.railway.wm.dao.UserRepository;
import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.domain.User;
import com.railway.wm.service.FileProgressService;
import jdk.internal.instrumentation.TypeMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	AnalyseRepository analyseRepository;
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
		fileProgressService.scanFileDirectAndResultIntoDB();
	}
	@Test
	public void  sqlTest(){
		List<AnalyseResult> results=analyseRepository.findLatelyCheckResult();
		System.out.printf(results.size()+"+++++++++++++++++");
		AnalyseResult analyseResult=new AnalyseResult();
		analyseResult.setRailStation("hf");


	}

}
