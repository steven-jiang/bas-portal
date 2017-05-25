package com.kii.bas.portal.web.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import context.MockWebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MockWebConfig.class})
public class WebTestTemplate {
	
	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;
	
	@BeforeClass
	public static void setSystemProps() {
		System.setProperty("spring.profile", "local");
	}
	
	@Before
	public void before() {
		
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
}
