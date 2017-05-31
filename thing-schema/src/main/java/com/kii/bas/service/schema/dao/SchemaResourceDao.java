package com.kii.bas.service.schema.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kii.bas.commons.store.mongo.AbstractDataAccess;
import com.kii.bas.service.schema.entity.ResourceEntry;
import com.kii.bas.service.schema.entity.SchemaResource;

@Component
public class SchemaResourceDao extends AbstractDataAccess<SchemaResource> {
	
	
	@Override
	protected String getCollectName() {
		return "schemaResource";
	}
	
	
	public void updateByField(String schemaName, String fullField, ResourceEntry entry) {
		
		Map<String, Object> param = Collections.singletonMap(fullField, entry);
		super.updateOrInsertEntity(param, schemaName);
		
	}
	
	
	public Map<String, SchemaResource> getOriginSchemaMap() {
		
		Map<String, SchemaResource> originMap = new HashMap<>();
		
		
		super.iterateAll((d) -> {
			originMap.put(d.getId(), d);
		});
		
		return originMap;
		
	}
	
	public void removeByField(String schemaName, String fullField) {
		
		Map<String, Object> param = Collections.singletonMap(fullField, ResourceEntry.NULL);
		super.updateOrInsertEntity(param, schemaName);
	}
}
