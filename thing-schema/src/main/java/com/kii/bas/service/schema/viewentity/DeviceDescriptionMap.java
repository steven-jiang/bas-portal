package com.kii.bas.service.schema.viewentity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kii.bas.service.schema.dao.UpdatedInfo;
import com.kii.bas.service.schema.entity.DeviceDescription;
import com.kii.bas.service.schema.entity.DeviceSchema;

public class DeviceDescriptionMap {
	
	
	private Map<String, DeviceFullDescription> schemaMap = new HashMap<>();
	
	private Map<String, DeviceDescription> originMap = new HashMap();
	
	
	public DeviceDescriptionMap(Map<String, DeviceSchema> extendsData, Map<String, DeviceDescription> origin) {
		
		this.originMap = origin;
		
		for (Map.Entry<String, DeviceSchema> entry : extendsData.entrySet()) {
			DeviceDescription orig = origin.computeIfAbsent(entry.getKey(), (k) -> {
				return new DeviceDescription(entry.getValue());
			});
			
			DeviceDescription desc = new DeviceDescription(orig);
			schemaMap.put(entry.getKey(), combineSchema(desc, extendsData, schemaMap));
		}
		
	}
	
	public DeviceDescription getDescriptionByName(String name) {
		return originMap.get(name);
	}
	
	public Map<String, DeviceFullDescription> getFullDescriptionMap() {
		return schemaMap;
	}
	
	public Map<String, DeviceFullDescription> getUpdatedSchemas(UpdatedInfo info) {
		
		Map<String, DeviceFullDescription> map = new HashMap<>();
		info.getUpdated().forEach(k -> {
			map.put(k, schemaMap.get(k));
		});
		
		info.getDeleted().forEach(k -> {
			map.put(k, DeviceFullDescription.getDeletedInstance());
		});
		
		return map;
	}
	
	private DeviceFullDescription combineSchema(DeviceDescription schema, Map<String, DeviceSchema> extendsMap, Map<String, DeviceFullDescription> schemaMap) {
		
		DeviceSchema devSchema = extendsMap.get(schema.getDeviceType());
		
		String extendName = devSchema.getExtendsSchema();
		
		if (StringUtils.isNotBlank(extendName)) {
			DeviceFullDescription fullSchema = getSuperSchema(extendName, extendsMap, schemaMap);
			
			return new DeviceFullDescription(devSchema, schema, fullSchema);
		} else {
			return new DeviceFullDescription(devSchema, schema);
		}
	}
	
	private DeviceFullDescription getSuperSchema(String name, Map<String, DeviceSchema> extendsMap, Map<String, DeviceFullDescription> schemaMap) {
		
		return schemaMap.computeIfAbsent(name, (s) -> {
			
			return combineSchema(new DeviceDescription(originMap.get(s)), extendsMap, schemaMap);
		});
	}
}
