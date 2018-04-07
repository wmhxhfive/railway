package com.railway.wm;

import com.railway.wm.dao.UserRepository;
import com.railway.wm.domain.User;
import com.railway.wm.service.FileProgressService;
import jdk.internal.instrumentation.TypeMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WmApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	FileProgressService  fileProgressService;
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

}
