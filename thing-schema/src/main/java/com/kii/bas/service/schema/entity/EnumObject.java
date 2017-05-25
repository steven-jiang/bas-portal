package com.kii.bas.service.schema.entity;

import java.util.HashSet;
import java.util.Set;

public class EnumObject {
	
	private EnumType type;
	
	private Set<Object> enumValues = new HashSet<>();
	
	public EnumObject() {
		
	}
	
	public EnumObject(EnumObject obj) {
		this.type = obj.type;
		this.enumValues = obj.enumValues;
	}
	
	public EnumType getType() {
		return type;
	}
	
	public void setType(EnumType type) {
		this.type = type;
	}
	
	public Set<Object> getEnumValues() {
		return enumValues;
	}
	
	public void setEnumValues(Set<Object> enumValues) {
		this.enumValues = enumValues;
	}
	
	public enum EnumType {
		intEnum, strEnum;
	}
}
