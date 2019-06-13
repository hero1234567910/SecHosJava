package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.basic.javaframe.service.api.Wx_CommonServiceIApi;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	
	@Autowired
	private Wx_CommonServiceIApi wx_CommonServiceApi;
	
	@Test
	public void contextLoads() {
		
		
	}

}
