package com.kii.bas.service.schema.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class PointDescription {
	
	
	private String name;
	
	private String description;
	
	private DescriptionObj obj;
	
	public PointDescription(PointObject v) {
		this.name = v.getName();
		this.description = this.name;
		
		obj = new DescriptionObj(v.getObj());
		
	}
	
	
	public PointDescription() {
		
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
	
	@JsonUnwrapped
	public DescriptionObj getObj() {
		return obj;
	}
	
	public void setObj(DescriptionObj obj) {
		this.obj = obj;
	}
}
