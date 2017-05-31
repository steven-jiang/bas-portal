package com.kii.bas.service.schema.viewentity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;

import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.entity.SchemaResource;

public class DeviceResourceStore {
	
	private Map<String, DeviceResouceCollect> entityMap = new HashMap<>();
	
	
	public DeviceResourceStore(Map<String, DeviceSchema> extendsData, Map<String, SchemaResource> origin, BiFunction<String, String, String> baseUrl) {
		
		
		Map<String, DeviceResouceCollect> tempMap = new HashMap<>();
		for (Map.Entry<String, SchemaResource> entry : origin.entrySet()) {
			
			DeviceResouceCollect collect = new DeviceResouceCollect(entry.getValue());
			
			tempMap.put(entry.getKey(), collect);
		}
		
		
		for (String key : extendsData.keySet()) {
			entityMap.put(key, combineSchema(key, tempMap.get(key), tempMap, extendsData));
		}
		
		entityMap.replaceAll((k, entity) -> {
			entity.bindUrl(k, baseUrl);
			return entity;
		});
	}
	
	
	private DeviceResouceCollect combineSchema(String name, DeviceResouceCollect schema, Map<String, DeviceResouceCollect> schemaMap, Map<String, DeviceSchema> extendsMap) {
		
		String extendName = extendsMap.get(name).getExtendsSchema();
		
		if (StringUtils.isNotBlank(extendName)) {
			DeviceResouceCollect superSchema = getSuperSchema(extendName, schemaMap, extendsMap);
			
			schema.combineSuperResource(superSchema);
			return schema;
		} else {
			return schema;
		}
	}
	
	private DeviceResouceCollect getSuperSchema(String name, Map<String, DeviceResouceCollect> schemaMap, Map<String, DeviceSchema> extendsMap) {
		
		return schemaMap.computeIfAbsent(name, (s) -> {
			return combineSchema(name, DeviceResouceCollect.EMPTY, schemaMap, extendsMap);
		});
	}
	
	
	public DeviceResouceCollect getEntityByName(String name) {
		return entityMap.getOrDefault(name, DeviceResouceCollect.EMPTY);
	}
	
	
}
