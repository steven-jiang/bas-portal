package com.kii.bas.thschema.test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kii.bas.service.schema.dao.SchemaResourceDao;
import com.kii.bas.service.schema.entity.ResourceEntry;
import com.kii.bas.service.schema.entity.SchemaResource;

public class TestResourceDao extends ThSchemaTestTemplate {
	
	@Autowired
	private SchemaResourceDao dao;
	
	
	@Test
	public void addInfo() {
		
		ResourceEntry entry = new ResourceEntry();
		entry.setMediaType("image/jpeg");
		entry.setSize(10l);
		entry.setType(ResourceEntry.ResourceType.Resource);
		
		String name = "foo";
		dao.updateByField(name, "points.lights.refPointDict.50", entry);
		dao.updateByField(name, "points.lights.enumDict.50", entry);
		dao.updateByField(name, "resource", entry);
		
		dao.updateByField(name, "actions.powerOn.params.power.ON", entry);
		
		Map<String, SchemaResource> map = dao.getOriginSchemaMap();
		
		SchemaResource resc = map.get(name);
		
		assertNotNull(resc);
		
		assertEquals(resc.getPoints().get("lights").getObj().getEnumDict().get("50").getSize(), 10l);
	}
	
}
