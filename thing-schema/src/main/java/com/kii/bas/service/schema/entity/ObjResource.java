package com.kii.bas.service.schema.entity;

import java.util.HashMap;
import java.util.Map;

public class ObjResource {
	
	
	private Map<String, ResourceEntry> enumDict = new HashMap<>();
	
	private Map<String, ResourceEntry> refPointDict = new HashMap<>();
	
	public Map<String, ResourceEntry> getEnumDict() {
		return enumDict;
	}
	
	public void setEnumDict(Map<String, ResourceEntry> enumDict) {
		this.enumDict = enumDict;
	}
	
	public Map<String, ResourceEntry> getRefPointDict() {
		return refPointDict;
	}
	
	public void setRefPointDict(Map<String, ResourceEntry> refPointDict) {
		this.refPointDict = refPointDict;
	}
}
