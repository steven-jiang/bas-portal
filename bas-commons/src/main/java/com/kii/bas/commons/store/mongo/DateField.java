package com.kii.bas.commons.store.mongo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateField {
	
	private Date date;
	
	@JsonProperty("$date")
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
