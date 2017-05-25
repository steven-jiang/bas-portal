package com.kii.bas.service.schema.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class PointObject {
	
	
	private String name;
	
	private Boolean writable;
	
	private String writeActionName;
	
	private Object initValue;
	
	private SchemaObj obj;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getWritable() {
		return writable;
	}
	
	public void setWritable(Boolean writable) {
		this.writable = writable;
	}
	
	public String getWriteActionName() {
		return writeActionName;
	}
	
	public void setWriteActionName(String writeActionName) {
		this.writeActionName = writeActionName;
	}
	
	public Object getInitValue() {
		return initValue;
	}
	
	public void setInitValue(Object initValue) {
		this.initValue = initValue;
	}
	
	
	@JsonUnwrapped
	public SchemaObj getObj() {
		return obj;
	}
	
	public void setObj(SchemaObj obj) {
		this.obj = obj;
	}
}
