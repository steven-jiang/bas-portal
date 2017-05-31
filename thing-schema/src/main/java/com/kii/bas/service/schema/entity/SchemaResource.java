package com.kii.bas.service.schema.entity;

import java.util.HashMap;
import java.util.Map;

import com.kii.bas.commons.store.mongo.StoreEntity;

public class SchemaResource extends StoreEntity {
	
	private ResourceEntry resource;
	
	private Map<String, ResourceEntry> additionInfos = new HashMap<>();
	
	private Map<String, ResourceEntry> errorInfo = new HashMap<>();
	
	private Map<String, PointResource> points = new HashMap<>();
	
	private Map<String, ActionResource> actions = new HashMap<>();
	
	
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
	
	public Map<String, ResourceEntry> getErrorInfo() {
		return errorInfo;
	}
	
	public void setErrorInfo(Map<String, ResourceEntry> errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public Map<String, PointResource> getPoints() {
		return points;
	}
	
	public void setPoints(Map<String, PointResource> points) {
		this.points = points;
	}
	
	public Map<String, ActionResource> getActions() {
		return actions;
	}
	
	public void setActions(Map<String, ActionResource> actions) {
		this.actions = actions;
	}
}
