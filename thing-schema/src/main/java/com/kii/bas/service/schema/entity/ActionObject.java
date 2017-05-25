package com.kii.bas.service.schema.entity;

import java.util.HashMap;
import java.util.Map;

public class ActionObject {
	
	private String name;
	
	private Map<String, SchemaObj> fields = new HashMap<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, SchemaObj> getFields() {
		return fields;
	}
	
	public void setFields(Map<String, SchemaObj> fields) {
		this.fields = fields;
	}
}
