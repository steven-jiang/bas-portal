package com.kii.bas.service.schema.viewentity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import com.kii.bas.service.schema.entity.PointDescription;
import com.kii.bas.service.schema.entity.PointObject;

public class PointFullDescription {
	
	
	private String name;
	
	private String description;
	
	private Boolean writable;
	
	private String writeActionName;
	
	private Object initValue;
	
	
	private FullDescriptionObj object;
	
	public PointFullDescription(PointObject point, PointDescription desc) {
		this.name = point.getName();
		this.description = desc.getDescription();
		this.initValue = point.getInitValue();
		this.writable = point.getWritable();
		this.writeActionName = point.getWriteActionName();
		
		this.object = new FullDescriptionObj(point.getObj(), desc.getObj());
	}
	
	public PointFullDescription(PointObject point, PointFullDescription desc) {
		this.name = point.getName();
		this.description = desc.getDescription();
		this.initValue = point.getInitValue();
		this.writable = point.getWritable();
		this.writeActionName = point.getWriteActionName();
		
		this.object = new FullDescriptionObj(point.getObj(), desc.getObject().getDescObj());
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Boolean getWritable() {
		return writable;
	}
	
	public String getWriteActionName() {
		return writeActionName;
	}
	
	public Object getInitValue() {
		return initValue;
	}
	
	@JsonUnwrapped
	public FullDescriptionObj getObject() {
		return object;
	}
}
