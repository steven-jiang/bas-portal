package com.kii.bas.commons.store.mongo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import com.kii.bas.commons.store.entity.StatusType;


public class BusinessEntity extends StoreEntity {
	
	
	private StatusType recordStatus = StatusType.enable;
	
	private DateField createDate;
	
	
	@JsonSetter("_id")
	public void setIdPoint(IDField field) {
		this.id = field.getId();
	}
	
	
	@JsonGetter("createDate")
	public Date getCreateDate() {
		if (createDate != null) {
			return createDate.getDate();
		} else {
			return null;
		}
	}
	
	@JsonSetter("createDate")
	public void setCreateDateField(DateField createDate) {
		this.createDate = createDate;
	}
	
	public StatusType getRecordStatus() {
		return recordStatus;
	}
	
	public void setRecordStatus(StatusType recordStatus) {
		this.recordStatus = recordStatus;
	}
}
