package com.kii.bas.service.schema.entity;

public class ReferenceObject {
	
	private String name;
	
	private String refSchema;
	
	private ReferenceType type;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRefSchema() {
		return refSchema;
	}
	
	public void setRefSchema(String refSchema) {
		this.refSchema = refSchema;
	}
	
	public ReferenceType getType() {
		return type;
	}
	
	public void setType(ReferenceType type) {
		this.type = type;
	}
	
	enum ReferenceType {
		belong;
	}
}
