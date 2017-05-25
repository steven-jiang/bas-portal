package com.kii.bas.service.schema.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kii.bas.commons.store.mongo.AbstractDataAccess;
import com.kii.bas.service.schema.entity.DeviceDescription;

@Component
public class DeviceDescriptionDao extends AbstractDataAccess<DeviceDescription> {
	
	
	protected String getCollectName() {
		
		return "deviceDescription";
	}
	
	;
	
	
	public Map<String, DeviceDescription> getOriginSchemaMap(String location) {
		
		Map<String, DeviceDescription> originMap = new HashMap<>();
		
		
		super.iterate((d) -> {
			originMap.put(d.getDeviceType(), d);
		},eq("delete",false),null);
		
		return originMap;
		
	}
	
	public void replaceSchema(DeviceDescription description) {
		
		super.replaceEntity(description, description.getFullDescriptionName());
	}
	
	public void updateSchema(DeviceDescription description, String name, String location) {
		
		description.setDeviceType(name);
		description.setLocation(location);
		
		String id = description.getFullDescriptionName();
		super.updateEntity(description, id);
	}
	
	public void deleteSchema(String deviceType, String location) {
		
		DeviceDescription description = new DeviceDescription();
		description.setDeviceType(deviceType);
		description.setLocation(location);
		
		String id = description.getFullDescriptionName();
		
		super.deleteEntity(id);
	}
	
	
	public UpdatedInfo getModifiedSchemaAfterTime(Date time) {
		
		
		UpdatedInfo set = new UpdatedInfo();
		
		
		super.iterate((i) -> {
			set.addEntity(i, i.getDeviceType());
		}, gt("modifyDate", time),null);
		
		return set;
		
	}
}
