package com.kii.bas.commons.sysmonitor;

import java.text.DateFormat;
import java.util.Date;

public class SysMonitorMsg {
	
	private Date fireDate=new Date();
	private FromType from;
	private String errMessage;
	private String errorType;
	private String time;
	
	public String getTime() {
		return DateFormat.getTimeInstance().format(fireDate);
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public Date getFireDate() {
		return fireDate;
	}
	
	public void setFireDate(Date fireDate) {
		this.fireDate = fireDate;
	}
	
	public FromType getFrom() {
		return from;
	}
	
	public void setFrom(FromType from) {
		this.from = from;
	}
	
	public String getErrMessage() {
		return errMessage;
	}
	
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	public String getErrorType() {
		return errorType;
	}
	
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	public enum FromType {
		RuleEngine, Drools, DB, KiiApp, Auth, ES;
	}
}
