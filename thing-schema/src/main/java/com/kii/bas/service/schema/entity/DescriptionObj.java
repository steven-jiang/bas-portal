package com.kii.bas.service.schema.entity;

import java.util.HashMap;
import java.util.Map;

public class DescriptionObj {
	
	
	private String unit;
	
	private String format;
	
	private Map<String, String> enumDict = new HashMap<>();
	
	public DescriptionObj(SchemaObj obj) {
		
		unit = null;
		format = null;
		
		obj.getEnumValues().forEach(s -> {
			String val = String.valueOf(s);
			enumDict.put(val, val);
		});
	}
	
	public DescriptionObj() {
		
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public Map<String, String> getEnumDict() {
		return enumDict;
	}
	
	public void setEnumDict(Map<String, String> enumDict) {
		this.enumDict = enumDict;
	}
	
	
}
