package com.kii.bas.service.schema.exception;

public class FailReason {
	
	
	private Reason reason;
	
	private ObjType type;
	
	private String schema;
	
	private String operateName;
	
	private String fieldName;
	
	private Object value;
	
	public FailReason() {
		
	}
	
	public static FailReason getSchemaInstance(String schema) {
		FailReason reason = new FailReason();
		reason.setSchema(schema);
		reason.setType(ObjType.Schema);
		reason.setReason(Reason.NameNotExist);
		
		return reason;
	}
	
	public static FailReason getPointInstance(String schema, String point) {
		FailReason reason = new FailReason();
		reason.setSchema(schema);
		reason.setFieldName(point);
		reason.setOperateName(point);
		
		reason.setType(ObjType.Point);
		reason.setReason(Reason.NameNotExist);
		
		
		return reason;
	}
	
	public static FailReason getActionInstance(String schema, String action) {
		FailReason reason = new FailReason();
		reason.setSchema(schema);
		reason.setOperateName(action);
		
		reason.setType(ObjType.Action);
		reason.setReason(Reason.NameNotExist);
		
		return reason;
	}
	
	public static FailReason getActionInstance(String schema, String action, String fieldName) {
		FailReason reason = new FailReason();
		reason.setSchema(schema);
		reason.setOperateName(action);
		reason.setFieldName(fieldName);
		
		reason.setType(ObjType.Action);
		reason.setReason(Reason.NameNotExist);
		
		return reason;
	}
	
	public String getOperateName() {
		return operateName;
	}
	
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public Reason getReason() {
		return reason;
	}
	
	public void setReason(Reason reason) {
		this.reason = reason;
	}
	
	public ObjType getType() {
		return type;
	}
	
	public void setType(ObjType type) {
		this.type = type;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public enum Reason {
		
		NameNotExist, InvalidOperate, InvalidType, NullValue, NotInEnum, OutOfRange, Right;
		
	}
	
	public enum ObjType {
		
		Schema, Point, Action;
	}
}
