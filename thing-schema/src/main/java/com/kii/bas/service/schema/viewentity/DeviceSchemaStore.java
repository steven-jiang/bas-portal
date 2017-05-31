package com.kii.bas.service.schema.viewentity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.kii.bas.service.schema.dao.UpdatedInfo;
import com.kii.bas.service.schema.entity.DeviceSchema;

public class DeviceSchemaStore {
	
	
	private final Map<String, DeviceSchema> originMap;
	
	private final Map<String, DeviceSchema> schemaMap = new HashMap<>();
	
	private final Map<String, Set<String>> treeIndex = new HashMap<>();
	
	
	public DeviceSchemaStore(Map<String, DeviceSchema> input) {
		
		this.originMap = input;
		
		Map<String, Set<String>> extendMap = new HashMap<>();
		for (Map.Entry<String, DeviceSchema> entry : originMap.entrySet()) {
			DeviceSchema schema = new DeviceSchema(entry.getValue());
			
			if (StringUtils.isNotBlank(schema.getExtendsSchema())) {
				extendMap.computeIfAbsent(schema.getExtendsSchema(), (k) -> {
					return new HashSet<>();
				}).add(schema.getDeviceType());
			}
			
			schemaMap.put(entry.getKey(), combineSchema(schema));
		}
		
		fillTreeIndex(extendMap);
		
	}
	
	public Map<String, DeviceSchema> getCompleteSchemaMap() {
		return schemaMap;
	}
	
	public DeviceSchema getOriginDeviceSchema(String name) {
		return originMap.get(name);
	}
	
	public DeviceSchema getCompleteDeviceSchema(String name) {
		return schemaMap.get(name);
	}
	
	public Map<String, DeviceSchema> getUpdatedSchemas(UpdatedInfo info) {
		
		Map<String, DeviceSchema> map = new HashMap<>();
		
		info.getUpdated().forEach(k -> {
			map.put(k, schemaMap.get(k));
			
			Set<String> subs = treeIndex.get(k);
			
			subs.forEach((s) -> {
				map.put(s, schemaMap.get(s));
			});
		});
		
		
		info.getDeleted().forEach(k -> {
			map.put(k, DeviceSchema.getDeletedInst());
		});
		
		return map;
	}
	
	private void fillTreeIndex(Map<String, Set<String>> extendMap) {
		
		for (Map.Entry<String, Set<String>> entry : extendMap.entrySet()) {
			
			Set<String> set = new HashSet<>();
			set.addAll(entry.getValue());
			fillChildSet(extendMap, set);
			treeIndex.put(entry.getKey(), set);
		}
	}
	
	private void fillChildSet(Map<String, Set<String>> extendMap, Set<String> resultSet) {
		
		int oldSize = resultSet.size();
		for (String key : resultSet) {
			
			Set<String> nextSet = treeIndex.computeIfAbsent(key, (k) -> {
				Set<String> newSet = new HashSet<>();
				Set<String> orig = extendMap.get(k);
				if (orig != null) {
					newSet.addAll(orig);
					fillChildSet(extendMap, newSet);
				}
				return newSet;
			});
			
			resultSet.addAll(nextSet);
		}
		
		if (resultSet.size() > oldSize) {
			fillChildSet(extendMap, resultSet);
		}
	}
	
	private DeviceSchema combineSchema(DeviceSchema schema) {
		
		if (StringUtils.isBlank(schema.getExtendsSchema())) {
			return schema;
		}
		
		DeviceSchema superSchema = getSuperSchema(schema.getExtendsSchema());
		
		return schema.combineSuperSchema(superSchema);
	}
	
	private DeviceSchema getSuperSchema(String name) {
		
		return schemaMap.computeIfAbsent(name, (s) -> {
			
			return combineSchema(new DeviceSchema(originMap.get(s)));
		});
	}
}
