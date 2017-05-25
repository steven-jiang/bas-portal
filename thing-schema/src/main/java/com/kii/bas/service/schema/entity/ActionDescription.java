package com.kii.bas.service.schema.entity;

import java.util.HashMap;
import java.util.Map;

public class ActionDescription {
	
	
	private String name;
	
	private String description;
	
	private Map<String, DescriptionObj> fields = new HashMap<>();
	
	public ActionDescription(ActionObject v) {
		
		name = v.getName();
		description = v.getName();
		
		v.getFields().forEach((k, val) -> {
			fields.put(k, new DescriptionObj(val));
		});
	}
	
	public ActionDescription() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Map<String, DescriptionObj> getFields() {
		return fields;
	}
	
	public void setFields(Map<String, DescriptionObj> fields) {
		this.fields = fields;
	}
}
