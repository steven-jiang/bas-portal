package com.kii.bas.service.schema.entity;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class PointResource {
	
	
	private ResourceEntry resource;
	
	private Map<String, ResourceEntry> additionInfos = new HashMap<>();
	
	private ObjResource obj;
	
	
	public ResourceEntry getResource() {
		return resource;
	}
	
	public void setResource(ResourceEntry resource) {
		this.resource = resource;
	}
	
	public Map<String, ResourceEntry> getAdditionInfos() {
		return additionInfos;
	}
	
	public void setAdditionInfos(Map<String, ResourceEntry> additionInfos) {
		this.additionInfos = additionInfos;
	}
	
	@JsonUnwrapped
	public ObjResource getObj() {
		return obj;
	}
	
	public void setObj(ObjResource obj) {
		this.obj = obj;
	}
}
