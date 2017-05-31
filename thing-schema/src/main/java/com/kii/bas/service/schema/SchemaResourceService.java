package com.kii.bas.service.schema;

import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.kii.bas.commons.cache.CacheConfig;
import com.kii.bas.service.schema.dao.DeviceTypeDao;
import com.kii.bas.service.schema.dao.SchemaResourceDao;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.entity.ResourceEntry;
import com.kii.bas.service.schema.entity.SchemaResource;
import com.kii.bas.service.schema.viewentity.DeviceResourceStore;

@Component
public class SchemaResourceService {
	
	
	@Autowired
	private DeviceTypeDao schemaDao;
	
	
	@Autowired
	private SchemaResourceDao resourceDao;
	
	
	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "schema_full_resource_complete_map")
	public void putResourceIndex(String name, String path, ResourceEntry entry) {
		
		resourceDao.updateByField(name, path, entry);
	}
	
	
	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "schema_full_resource_complete_map")
	public void deleteDeviceDescription(String name, String path) {
		
		resourceDao.removeByField(name, path);
	}
	
	
	@Cacheable(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "schema_full_resource_complete_map")
	public DeviceResourceStore getCompleteSchemaMap(BiFunction<String, String, String> urlTemplate) {
		
		Map<String, DeviceSchema> extendsMap = schemaDao.getCompleteSchemaMap().getCompleteSchemaMap();
		
		Map<String, SchemaResource> originMap = resourceDao.getOriginSchemaMap();
		
		
		return new DeviceResourceStore(extendsMap, originMap, urlTemplate);
	}
	
	
}
