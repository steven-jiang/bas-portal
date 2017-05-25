package com.kii.bas.service.schema;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kii.bas.service.schema.dao.DeviceTypeDao;
import com.kii.bas.service.schema.dao.UpdatedInfo;
import com.kii.bas.service.schema.entity.DeviceDescription;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.viewentity.DeviceFullDescription;

@Component
public class ThingSchemaService {
	
	
	@Autowired
	private DeviceTypeDao schemaDao;
	
	
	@Autowired
	private DescriptionSchemaService descriptionService;
	
	
	public void patchDeviceType(DeviceSchema schema, String schemaName) {
		
		schemaDao.updateSchema(schema, schemaName);
	}
	
	public void patchDeviceDescription(DeviceDescription description, String name, String location) {
		
		descriptionService.updateDeviceDescription(description, name, location);
	}
	
	public void deleteSchema(String schemaName) {
		schemaDao.deleteSchema(schemaName);
	}
	
	public void deleteDescription(String schemaName, String location) {
		
		descriptionService.deleteDeviceDescription(schemaName, location);
	}
	
	public void putDeviceType(DeviceSchema schema) {
		schemaDao.replaceSchema(schema);
	}
	
	public void putDeviceDescription(DeviceDescription description) {
		
		descriptionService.putDeviceDescription(description);
	}
	
	public DeviceDescription getDeviceDescription(String name, Locale locate) {
		
		return descriptionService.getCompleteSchemaMap(locate.toString()).getDescriptionByName(name);
	}
	
	
	public DeviceSchema getCompleteDeviceSchema(String name) {
		
		return schemaDao.getCompleteSchemaMap().getCompleteDeviceSchema(name);
	}
	
	public DeviceSchema getOriginDeviceSchema(String name) {
		
		return schemaDao.getCompleteSchemaMap().getOriginDeviceSchema(name);
	}
	
	
	public Map<String, DeviceFullDescription> getUpdatedFullDescriptionMap(Date timeStamp, Locale locate) {
		
		UpdatedInfo descSet = descriptionService.getUpdatedDescription(timeStamp);
		
		return descriptionService.getCompleteSchemaMap(locate.toString()).getUpdatedSchemas(descSet);
	}
	
	public Map<String, DeviceSchema> getUpdatedCompleteSchemaMap(Date timeStamp) {
		
		UpdatedInfo updatedName = schemaDao.getModifiedSchemaAfterTime(timeStamp);
		
		return schemaDao.getCompleteSchemaMap().getUpdatedSchemas(updatedName);
	}
	
	public Map<String, DeviceFullDescription> getFullDescriptionMap(Locale locate) {
		
		
		return descriptionService.getCompleteSchemaMap(locate.toString()).getFullDescriptionMap();
	}
	
	public Map<String, DeviceSchema> getCompleteSchemaMap() {
		
		return schemaDao.getCompleteSchemaMap().getCompleteSchemaMap();
	}
}
