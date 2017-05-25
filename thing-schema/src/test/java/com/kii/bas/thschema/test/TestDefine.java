package com.kii.bas.thschema.test;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.kii.bas.service.schema.ThingSchemaService;
import com.kii.bas.service.schema.entity.ActionDescription;
import com.kii.bas.service.schema.entity.ActionObject;
import com.kii.bas.service.schema.entity.DescriptionObj;
import com.kii.bas.service.schema.entity.DeviceDescription;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.entity.EnumObject;
import com.kii.bas.service.schema.entity.PointDescription;
import com.kii.bas.service.schema.entity.PointObject;
import com.kii.bas.service.schema.entity.SchemaObj;
import com.kii.bas.service.schema.entity.ThingDataType;
import com.kii.bas.service.schema.viewentity.DeviceFullDescription;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:testContext.xml"})
public class TestDefine extends ThSchemaTestTemplate {
	
	
	@Autowired
	private ThingSchemaService service;
	private MongoDatabase database;
	
	@BeforeClass
	public static void initProfile() {
		
		System.setProperty("spring.profile", "local");
	}
	
	@Test
	public void testPut() {
		
		
		MongoCollection collection = new MockCollection();

//		when(database.getCollection(any(String.class))).thenReturn(collection);
		
		
		{
			DeviceSchema schema = new DeviceSchema();
			
			schema.setDeviceType("lighting");
			
			List<PointObject> plist = new ArrayList<>();
			plist.add(getPower());
			
			schema.setPoints(plist);
			
			schema.setErrorInfo(getError());
			
			service.putDeviceType(schema);
			
			DeviceSchema s = service.getOriginDeviceSchema("lighting");
			assertEquals(s.getPoints().size(), 1);
		}
		
		{
			DeviceSchema schema1 = new DeviceSchema();
			schema1.setDeviceType("whiteLighting");
			schema1.setExtendsSchema("lighting");
			schema1.addPoint(getBri());
			
			service.putDeviceType(schema1);
			
			
			DeviceSchema s = service.getOriginDeviceSchema("whiteLighting");
			assertEquals(s.getPoints().size(), 1);
			
			assertEquals("bri", s.getPoints().iterator().next().getName());
		}
		
		{
			DeviceSchema schema2 = new DeviceSchema();
			schema2.setExtendsSchema("whiteLighting");
			schema2.setDeviceType("colorLighting");
			
			schema2.addPoint(getColor());
			
			service.putDeviceType(schema2);
			
			DeviceSchema s = service.getOriginDeviceSchema("colorLighting");
			assertEquals(s.getPoints().size(), 1);
			
			assertEquals("color", s.getPoints().iterator().next().getName());
			
		}
		{
			DeviceSchema schema3 = new DeviceSchema();
			schema3.setDeviceType("modeLighting");
			
			schema3.setExtendsSchema("colorLighting");
			
			schema3.addPoint(getLight());
			
			schema3.addPoint(getMode());
			
			service.putDeviceType(schema3);
			
			DeviceSchema s = service.getOriginDeviceSchema("modeLighting");
			assertEquals(s.getPoints().size(), 2);
			
			
		}
		
		{
			DeviceSchema schema3 = new DeviceSchema();
			schema3.setDeviceType("switchLighting");
			
			schema3.setExtendsSchema("colorLighting");
			
			schema3.addAction(getSwitch());
			schema3.addPoint(getMode());
			schema3.setErrorInfo(getError2());
			
			service.putDeviceType(schema3);
			
			DeviceSchema s = service.getOriginDeviceSchema("switchLighting");
			assertEquals(s.getActions().size(), 1);
			
			assertEquals("switch", s.getActions().iterator().next().getName());
			
		}
		
		{
			DeviceSchema fullSchema = service.getCompleteDeviceSchema("switchLighting");
			
			assertEquals(4, fullSchema.getPoints().size());
			
			assertEquals(1, fullSchema.getActions().size());
			
		}
		
		Date time1 = new Date();
		{
			
			Map<String, DeviceSchema> map1 = service.getUpdatedCompleteSchemaMap(time1);
			assertEquals(map1.size(), 0);
		}
		{
			DeviceSchema schema = new DeviceSchema();
			
			schema.setDeviceType("lighting");
			
			List<PointObject> plist = new ArrayList<>();
			plist.add(getPower());
			plist.get(0).setInitValue(11);
			
			schema.setPoints(plist);
			
			schema.setErrorInfo(getError());
			
			service.putDeviceType(schema);
			
			Map<String, DeviceSchema> map1 = service.getUpdatedCompleteSchemaMap(time1);
			assertEquals(map1.size(), 5);
		}

	}
	
	
	private EnumObject getError() {
		EnumObject obj = new EnumObject();
		obj.setType(EnumObject.EnumType.intEnum);
		Set<Object> set = new HashSet<>();
		set.addAll(Arrays.asList(new Integer[]{100, 200, 300, 400, 500}));
		obj.setEnumValues(set);
		
		return obj;
	}
	
	private EnumObject getError2() {
		EnumObject obj = new EnumObject();
		obj.setType(EnumObject.EnumType.intEnum);
		Set<Object> set = new HashSet<>();
		set.addAll(Arrays.asList(new Integer[]{10, 20, 30, 40, 50}));
		obj.setEnumValues(set);
		
		return obj;
	}
	
	private ActionObject getSwitch() {
		
		ActionObject act = new ActionObject();
		act.setName("switch");
		
		Map<String, SchemaObj> fieldMap = new HashMap<>();
		SchemaObj obj = new SchemaObj();
		obj.setType(ThingDataType.IntEnum);
		
		Set<Integer> set = new HashSet<>();
		set.add(3);
		set.add(5);
		set.add(7);
		obj.setEnumValues(set);
		fieldMap.put("switchMode", obj);
		
		SchemaObj param = new SchemaObj();
		param.setType(ThingDataType.Int);
		param.setLowerLimit(1);
		param.setUpperLimit(10);
		obj.setEnumValues(set);
		fieldMap.put("interval", obj);
		
		act.setFields(fieldMap);
		
		return act;
	}
	
	
	private PointObject getPower() {
		
		PointObject bri = new PointObject();
		
		bri.setName("power");
		
		SchemaObj obj = new SchemaObj();
		obj.setType(ThingDataType.Boolean);
		
		bri.setObj(obj);
		bri.setWritable(true);
		return bri;
	}
	
	private PointObject getBri() {
		
		PointObject bri = new PointObject();
		
		bri.setName("bri");
		
		SchemaObj obj = new SchemaObj();
		obj.setLowerLimit(0);
		obj.setUpperLimit(100);
		obj.setType(ThingDataType.Int);
		
		bri.setObj(obj);
		bri.setWritable(true);
		
		return bri;
	}
	
	private PointObject getColor() {
		
		PointObject point = new PointObject();
		
		point.setName("color");
		
		SchemaObj obj = new SchemaObj();
		Set<Integer> s = new HashSet<>();
		s.add(0);
		s.add(1);
		s.add(3);
		s.add(5);
		obj.setEnumValues(s);
		obj.setType(ThingDataType.IntEnum);
		
		point.setObj(obj);
		point.setWritable(true);
		
		return point;
	}
	
	private PointObject getMode() {
		
		PointObject point = new PointObject();
		
		point.setName("mode");
		
		SchemaObj obj = new SchemaObj();
		Set<String> s = new HashSet<>();
		s.add("Red");
		s.add("Blue");
		s.add("White");
		s.add("Yellow");
		obj.setEnumValues(s);
		obj.setType(ThingDataType.String);
		
		point.setObj(obj);
		point.setWritable(true);
		
		return point;
	}
	
	private PointObject getLight() {
		
		PointObject point = new PointObject();
		
		point.setName("light");
		
		SchemaObj obj = new SchemaObj();
		obj.setType(ThingDataType.Float);
		obj.setLowerLimit(0);
		obj.setUpperLimit(8000);
		
		point.setObj(obj);
		point.setWritable(true);
		
		return point;
	}
	
	
	@Test
	public void putDescription() {
		
		testPut();
		
		String loc = Locale.SIMPLIFIED_CHINESE.toLanguageTag();
//		MongoCollection defineCol=new MockCollection();
//
//		when(database.getCollection(eq("deviceSchema"))).thenReturn(defineCol);
//
//		MongoCollection descCol=new MockCollection();
//
//		when(database.getCollection(eq("deviceDescription"))).thenReturn(descCol);
		
		
		{
			DeviceDescription schema = new DeviceDescription();
			schema.setLocation(loc);
			
			schema.setDeviceType("lighting");
			
			List<PointDescription> plist = new ArrayList<>();
			plist.add(getPowerD());
			
			schema.setPoints(plist);
			
			schema.setErrorInfoDict(getErrorD());
			
			service.putDeviceDescription(schema);
			
			DeviceSchema s = service.getOriginDeviceSchema("lighting");
			assertEquals(s.getPoints().size(), 1);
		}
		
		{
			DeviceDescription schema1 = new DeviceDescription();
			schema1.setDeviceType("whiteLighting");
			schema1.addPoint(getBriD());
			schema1.setLocation(loc);
			
			service.putDeviceDescription(schema1);
			
			
			DeviceSchema s = service.getOriginDeviceSchema("whiteLighting");
			assertEquals(s.getPoints().size(), 1);
			
			assertEquals("bri", s.getPoints().iterator().next().getName());
		}
		
		{
			DeviceDescription schema2 = new DeviceDescription();
			schema2.setDeviceType("colorLighting");
			schema2.setLocation(loc);
			
			schema2.addPoint(getColorD());
			
			service.putDeviceDescription(schema2);
			
			DeviceSchema s = service.getOriginDeviceSchema("colorLighting");
			assertEquals(s.getPoints().size(), 1);
			
			assertEquals("color", s.getPoints().iterator().next().getName());
			
		}
		{
			DeviceDescription schema3 = new DeviceDescription();
			schema3.setDeviceType("modeLighting");
			schema3.setLocation(loc);
			
			schema3.addPoint(getLightD());
			
			schema3.addPoint(getModeD());
			
			service.putDeviceDescription(schema3);
			
			DeviceSchema s = service.getOriginDeviceSchema("modeLighting");
			assertEquals(s.getPoints().size(), 2);
			
			
		}
		
		Date date = new Date();
		
		{
			DeviceDescription schema3 = new DeviceDescription();
			schema3.setDeviceType("switchLighting");
			schema3.setLocation(loc);
			
			schema3.addAction(getSwitchD());
			schema3.addPoint(getModeD());
			schema3.setErrorInfoDict(getError2D());
			
			service.putDeviceDescription(schema3);
			
			DeviceSchema s = service.getOriginDeviceSchema("switchLighting");
			assertEquals(s.getActions().size(), 1);
			
			assertEquals("switch", s.getActions().iterator().next().getName());
			
		}
		
		
		{
			Map<String, DeviceFullDescription> fullMap = service.getFullDescriptionMap(Locale.SIMPLIFIED_CHINESE);
			
			DeviceFullDescription fullSchema = fullMap.get("switchLighting");
			
			assertEquals(4, fullSchema.getPoints().size());
			
			assertEquals(1, fullSchema.getActions().size());
			
			assertEquals("error1", fullSchema.getErrorInfoDict().get("10"));
			
		}
		
		{
			
			
			Map<String, DeviceFullDescription> map1 = service.getUpdatedFullDescriptionMap(date, Locale.SIMPLIFIED_CHINESE);
			
			assertEquals(map1.size(), 1);
			
		}
		
	}
	
	private PointDescription getBriD() {
		
		PointDescription point = new PointDescription();
		point.setName("bri");
		point.setDescription("the bri");
		
		DescriptionObj obj = new DescriptionObj();
		obj.setUnit("流明");
		
		point.setObj(obj);
		return point;
	}
	
	
	private PointDescription getLightD() {
		
		PointDescription point = new PointDescription();
		point.setName("light");
		point.setDescription("the bri");
		
		DescriptionObj obj = new DescriptionObj();
		obj.setUnit("流明");
		
		point.setObj(obj);
		return point;
	}
	
	private Map<String, String> getErrorD() {
		Map<String, String> obj = new HashMap<>();
		
		obj.put("100", "error1");
		obj.put("200", "error2");
		obj.put("300", "error3");
		obj.put("400", "error4");
		obj.put("500", "error5");
		
		return obj;
	}
	
	private Map<String, String> getError2D() {
		Map<String, String> obj = new HashMap<>();
		
		obj.put("10", "error1");
		obj.put("20", "error2");
		obj.put("30", "error3");
		obj.put("40", "error4");
		obj.put("50", "error5");
		
		return obj;
	}
	
	private ActionDescription getSwitchD() {
		
		ActionDescription act = new ActionDescription();
		act.setName("switch");
		
		Map<String, DescriptionObj> fieldMap = new HashMap<>();
		DescriptionObj obj = new DescriptionObj();
		
		Map<String, String> set = new HashMap<>();
		set.put("3", "modelThree");
		set.put("5", "modelFive");
		set.put("7", "modeSeven");
		obj.setEnumDict(set);
		fieldMap.put("switchMode", obj);
		
		DescriptionObj param = new DescriptionObj();
		param.setUnit("time interval");
		fieldMap.put("interval", obj);
		
		act.setFields(fieldMap);
		
		return act;
	}
	
	
	private PointDescription getPowerD() {
		
		PointDescription bri = new PointDescription();
		
		bri.setName("power");
		
		DescriptionObj obj = new DescriptionObj();
		Map<String, String> map = new HashMap<>();
		map.put("0", "关闭");
		map.put("1", "打开");
		obj.setEnumDict(map);
		bri.setObj(obj);
		return bri;
	}
	
	
	private PointDescription getColorD() {
		
		PointDescription point = new PointDescription();
		
		point.setName("color");
		
		DescriptionObj obj = new DescriptionObj();
		Map<Integer, String> s = new HashMap<>();
		s.put(0, "红色");
		s.put(1, "蓝色");
		s.put(3, "黄色");
		s.put(5, "白色");
		
		point.setObj(obj);
		
		return point;
	}
	
	private PointDescription getModeD() {
		
		PointDescription point = new PointDescription();
		
		point.setName("mode");
		
		DescriptionObj obj = new DescriptionObj();
		Map<String, String> s = new HashMap<>();
		
		s.put("Red", "红");
		s.put("Blue", "蓝");
		s.put("White", "白");
		s.put("Yellow", "黄");
		
		point.setObj(obj);
		
		return point;
	}
}
