package com.kii.bas.service.schema.entity;

import java.util.HashMap;
import java.util.Map;

public class ActionResource {
	
	private ResourceEntry resource;
	
	private Map<String, ObjResource> fields = new HashMap<>();
	
	private Map<String, ResourceEntry> additionInfos = new HashMap<>();
	
	public ResourceEntry getResource() {
		return resource;
	}
	
	public void setResource(ResourceEntry resource) {
		this.resource = resource;
	}
	
	public Map<String, ObjResource> getFields() {
		return fields;
	}
	
	public void setFields(Map<String, ObjResource> fields) {
		this.fields = fields;
	}
	
	public Map<String, ResourceEntry> getAdditionInfos() {
		return additionInfos;
	}
	
	public void setAdditionInfos(Map<String, ResourceEntry> additionInfos) {
		this.additionInfos = additionInfos;
	}
}
