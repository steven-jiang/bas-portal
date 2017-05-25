package com.kii.bas.thschema.test;


import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
public class ThSchemaTestTemplate {
	
	@BeforeClass
	public static void initProfile() {
		
		System.setProperty("spring.profile", "local");
	}


//	@Before
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//	}
}
