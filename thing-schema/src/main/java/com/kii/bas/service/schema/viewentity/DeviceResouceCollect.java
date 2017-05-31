package com.kii.bas.service.schema.viewentity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import com.kii.bas.service.schema.entity.ActionResource;
import com.kii.bas.service.schema.entity.ObjResource;
import com.kii.bas.service.schema.entity.PointResource;
import com.kii.bas.service.schema.entity.ResourceEntry;
import com.kii.bas.service.schema.entity.SchemaResource;

public class DeviceResouceCollect {
	
	public static DeviceResouceCollect EMPTY = new DeviceResouceCollect();
	private Map<String, FullResourceEntry> entityMap = new HashMap<>();
	
	
	private DeviceResouceCollect() {
	}
	
	public DeviceResouceCollect(SchemaResource resource) {
		
		String name = resource.getId();
		
		if (resource.getResource() != null) {
			addToMap(name, resource.getResource());
		}
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getAdditionInfos().entrySet()) {
			addToMap(name + ".additionInfos." + entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getErrorInfo().entrySet()) {
			addToMap(name + ".errorInfo." + entry.getKey(), entry.getValue());
		}
		
		
		for (Map.Entry<String, PointResource> entry : resource.getPoints().entrySet()) {
			
			fillPointResource(entry.getValue(), name + ".points." + entry.getKey());
		}
	}
	
	private void addToMap(String key, ResourceEntry entity) {
		
		if (entity.getType() != ResourceEntry.ResourceType.Null) {
			entityMap.put(key, new FullResourceEntry(entity));
		}
		
	}
	
	public void combineSuperResource(DeviceResouceCollect collect) {
		
		
		for (Map.Entry<String, FullResourceEntry> mapEntry : collect.entityMap.entrySet()) {
			
			FullResourceEntry entry = mapEntry.getValue();
			
			if (entry.getType() == ResourceEntry.ResourceType.NotInherited || entry.getType() == ResourceEntry.ResourceType.Nothing) {
				continue;
			}
			
			String key = mapEntry.getKey();
			
			entityMap.computeIfAbsent(key, (k) -> {
				return entry;
			});
		}
		
	}
	
	public void bindUrl(String name, BiFunction<String, String, String> baseUrl) {
		
		entityMap.replaceAll((k, entity) -> {
			entity.fillFullUrl(baseUrl, name, k);
			return entity;
		});
	}
	
	private void fillPointResource(PointResource resource, String prefix) {
		
		
		if (resource.getResource() != null) {
			addToMap(prefix, resource.getResource());
		}
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getAdditionInfos().entrySet()) {
			addToMap(prefix + ".additionInfos." + entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getObj().getEnumDict().entrySet()) {
			addToMap(prefix + ".enumDict." + entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getObj().getRefPointDict().entrySet()) {
			addToMap(prefix + ".refPointDict." + entry.getKey(), entry.getValue());
		}
		
	}
	
	private void fillActionResource(ActionResource resource, String prefix) {
		
		
		if (resource.getResource() != null) {
			addToMap(prefix, resource.getResource());
		}
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getAdditionInfos().entrySet()) {
			addToMap(prefix + ".additionInfos." + entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<String, ObjResource> entry : resource.getFields().entrySet()) {
			fillObjResource(prefix + ".fields." + entry.getKey(), entry.getValue());
		}
		
	}
	
	private void fillObjResource(String prefix, ObjResource resource) {
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getEnumDict().entrySet()) {
			addToMap(prefix + ".enumDict." + entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<String, ResourceEntry> entry : resource.getRefPointDict().entrySet()) {
			addToMap(prefix + ".refPointDict." + entry.getKey(), entry.getValue());
		}
		
	}
	
	public Map<String, FullResourceEntry> getEntityMap() {
		return entityMap;
	}
	
}
