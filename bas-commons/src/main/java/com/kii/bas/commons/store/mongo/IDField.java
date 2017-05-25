package com.kii.bas.commons.store.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IDField {
	
	
	private String id;
	
	@JsonProperty("$oid")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
