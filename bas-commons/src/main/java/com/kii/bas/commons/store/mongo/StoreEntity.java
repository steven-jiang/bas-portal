package com.kii.bas.commons.store.mongo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class StoreEntity {
	
	
	protected String id;
	
	private DateField modifyDate;
	
	
	@JsonGetter("_id")
	public String getId() {
		return id;
	}
	
	@JsonSetter("_id")
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonGetter("modifyDate")
	public Date getModifyDate() {
		if (modifyDate != null) {
			return modifyDate.getDate();
		} else {
			return null;
		}
	}
	
	@JsonSetter("modifyDate")
	public void setModifyDateField(DateField modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
