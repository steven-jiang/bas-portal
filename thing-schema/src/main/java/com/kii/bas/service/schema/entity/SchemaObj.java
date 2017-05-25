package com.kii.bas.service.schema.entity;

import java.util.HashSet;
import java.util.Set;

public class SchemaObj {
	
	private ThingDataType type;
	
	private Set<?> enumValues = new HashSet<>();
	
	private Number lowerLimit;
	
	private Number upperLimit;
	
	private Integer length;
	
	
	public ThingDataType getType() {
		return type;
	}
	
	public void setType(ThingDataType type) {
		this.type = type;
	}
	
	public Set<?> getEnumValues() {
		return enumValues;
	}
	
	public void setEnumValues(Set<?> enumValues) {
		this.enumValues = enumValues;
	}
	
	public Number getLowerLimit() {
		return lowerLimit;
	}
	
	public void setLowerLimit(Number lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	
	public Number getUpperLimit() {
		return upperLimit;
	}
	
	public void setUpperLimit(Number upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	public Integer getLength() {
		return length;
	}
	
	public void setLength(Integer length) {
		this.length = length;
	}
}
