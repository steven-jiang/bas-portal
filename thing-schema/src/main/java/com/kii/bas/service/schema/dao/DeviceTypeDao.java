package com.kii.bas.service.schema.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.kii.bas.commons.cache.CacheConfig;
import com.kii.bas.commons.store.mongo.AbstractDataAccess;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.viewentity.DeviceSchemaStore;

@Component
public class DeviceTypeDao extends AbstractDataAccess<DeviceSchema> {
	
	protected String getCollectName() {
		
		return "deviceSchema";
	}
	
	
	public DeviceSchema getSchemaByID(String id) {
		return super.getEntityByID(id);
	}

	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_complete_map'")
	public void replaceSchema(DeviceSchema schema) {
		
		super.replaceEntity(schema, schema.getDeviceType());
	}
	
	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_complete_map'")
	public void updateSchema(DeviceSchema schema, String name) {
		
		super.updateEntity(schema, name);
	}
	
	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_complete_map'")
	public void deleteSchema(String schemaName) {
		
		super.deleteEntity(schemaName);
	}
	
	private Map<String, DeviceSchema> getOriginSchemaMap() {
		
		Map<String, DeviceSchema> originMap = new HashMap<>();
		
		super.iterate((d) -> {
			originMap.put(d.getDeviceType(), d);
		},eq("delete",false),null);
		
		return originMap;
		
	}
	
	public UpdatedInfo getModifiedSchemaAfterTime(Date time) {
		
		UpdatedInfo set = new UpdatedInfo();
		
		super.iterate((i) -> {
			set.addEntity(i, i.getDeviceType());
		}, gt("modifyDate", time),null);
		
		return set;
		
	}
	
	
	@Cacheable(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_complete_map'")
	public DeviceSchemaStore getCompleteSchemaMap() {
		
		return new DeviceSchemaStore(getOriginSchemaMap());
	}
	

	
	
}
