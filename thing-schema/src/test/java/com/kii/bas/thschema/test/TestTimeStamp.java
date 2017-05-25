package com.kii.bas.thschema.test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kii.bas.service.schema.dao.DeviceTypeDao;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.entity.EnumObject;
import com.kii.bas.service.schema.entity.PointObject;
import com.kii.bas.service.schema.entity.SchemaObj;
import com.kii.bas.service.schema.entity.ThingDataType;

public class TestTimeStamp extends ThSchemaTestTemplate {
	
	@Autowired
	private DeviceTypeDao dao;
	
	
	private Logger log = LoggerFactory.getLogger(TestTimeStamp.class);
	
	@Test
	public void addTimestamp() {
		
		String name = "test_timeStamp";
		
		
		DeviceSchema schema = new DeviceSchema();
		
		schema.setDeviceType(name);
		
		List<PointObject> plist = new ArrayList<>();
		plist.add(getPower());
		
		schema.setPoints(plist);
		EnumObject e = new EnumObject();
		e.setType(EnumObject.EnumType.intEnum);
		
		schema.setErrorInfo(e);
		dao.replaceSchema(schema);
		
		
		DeviceSchema newSch = dao.getSchemaByID(name);
		
		assertEquals(name, newSch.getDeviceType());
		
		Date date1 = newSch.getModifyDate();
		log.info("time:" + newSch.getModifyDate());
		
		
		DeviceSchema update = new DeviceSchema();
		update.setErrorInfo(null);
		update.addPoint(getBri());
		
		dao.updateSchema(update, name);
		
		newSch = dao.getSchemaByID(name);
		assertEquals(1, newSch.getPoints().size());
		
		Date date2 = newSch.getModifyDate();
		
		assertTrue(!date1.equals(date2));
		
		
	}
	
	private PointObject getBri() {
		
		PointObject bri = new PointObject();
		
		bri.setName("bri");
		
		SchemaObj obj = new SchemaObj();
		obj.setType(ThingDataType.Boolean);
		
		bri.setObj(obj);
		bri.setWritable(true);
		return bri;
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
}
