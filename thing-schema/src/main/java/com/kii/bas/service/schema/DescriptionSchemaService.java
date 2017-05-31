package com.kii.bas.service.schema;


import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.kii.bas.commons.cache.CacheConfig;
import com.kii.bas.service.schema.dao.DeviceDescriptionDao;
import com.kii.bas.service.schema.dao.DeviceTypeDao;
import com.kii.bas.service.schema.dao.UpdatedInfo;
import com.kii.bas.service.schema.entity.DeviceDescription;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.viewentity.DeviceDescriptionStore;

@Component
public class DescriptionSchemaService {
	
	
	@Autowired
	private DeviceTypeDao schemaDao;
	
	
	@Autowired
	private DeviceDescriptionDao descriptionDao;
	
	
	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_description_complete_map'+#description.location")
	public void putDeviceDescription(DeviceDescription description) {
		
		descriptionDao.replaceSchema(description);
	}
	
	
	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_description_complete_map'+#location")
	public void updateDeviceDescription(DeviceDescription description, String name, String location) {
		
		descriptionDao.updateSchema(description, name, location);
	}
	
	
	@CacheEvict(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_description_complete_map'+#location")
	public void deleteDeviceDescription(String name, String location) {
		
		descriptionDao.deleteSchema(name, location);
	}
	
	
	@Cacheable(cacheNames = CacheConfig.LONGLIVE_CACHE, key = "'schema_full_description_complete_map'+#location")
	public DeviceDescriptionStore getCompleteSchemaMap(String location) {
		
		Map<String, DeviceSchema> extendsMap = schemaDao.getCompleteSchemaMap().getCompleteSchemaMap();
		
		Map<String, DeviceDescription> originMap = descriptionDao.getOriginSchemaMap(location);
		
		return new DeviceDescriptionStore(extendsMap, originMap);
	}
	
	
	public UpdatedInfo getUpdatedDescription(Date timeStamp) {
		
		UpdatedInfo info = new UpdatedInfo();
		
		UpdatedInfo updatedSet = descriptionDao.getModifiedSchemaAfterTime(timeStamp);
		
		UpdatedInfo updateSet2 = schemaDao.getModifiedSchemaAfterTime(timeStamp);
		
		info.setDeleted(updatedSet.getDeleted());
		info.getDeleted().addAll(updateSet2.getDeleted());
		
		info.getUpdated().addAll(updatedSet.getUpdated());
		info.getUpdated().addAll(updateSet2.getUpdated());
		
		return info;
	}
}
