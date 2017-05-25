package com.kii.bas.service.schema.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.kii.bas.commons.store.mongo.StoreEntity;


public class DeviceSchema extends StoreEntity {
	
	private static final DeviceSchema deletedInst = new DeviceSchema();
	
	static {
		
		deletedInst.setDelete(true);
		
	}
	
	private Map<String, PointObject> points = new HashMap<>();
	private Map<String, ActionObject> actions = new HashMap<>();
	private Map<String, ReferenceObject> references = new HashMap<>();
	private String errorInfoName;
	private EnumObject errorInfo = new EnumObject();
	private String deviceType;
	private String extendsSchema;
	private boolean delete;
	
	
	public DeviceSchema() {
		
	}

	
	public DeviceSchema(DeviceSchema schema) {
		this.points = new HashMap<>(schema.getPointsMap());
		this.actions = new HashMap<>(schema.getActionsMap());
		this.references = new HashMap<>(schema.getReferencesMap());
		this.errorInfoName = schema.getErrorInfoName();
		this.errorInfo = new EnumObject(schema.errorInfo);
		this.deviceType = schema.deviceType;
		this.extendsSchema = schema.extendsSchema;
	}
	
	public static DeviceSchema getDeletedInst() {
		return deletedInst;
	}
	
	public DeviceSchema combineSuperSchema(DeviceSchema superType) {
		
		superType.getPointsMap().forEach((k, p) -> {
			
			getPointsMap().computeIfAbsent(k, (name) -> {
				return p;
			});
			
		});
		
		superType.getActionsMap().forEach((k, a) -> {
			getActionsMap().computeIfAbsent(k, (name) -> {
				return a;
			});
		});
		
		if (getErrorInfo() == null) {
			setErrorInfo(superType.getErrorInfo());
		}
		
		superType.getReferencesMap().forEach((k, r) -> {
			getReferencesMap().computeIfAbsent(k, (name) -> {
				return r;
			});
		});
		
		return this;
		
	}
	
	
	public Collection<PointObject> getPoints() {
		return points.values();
	}
	
	public void setPoints(Collection<PointObject> points) {
		
		if (points == null) {
			return;
		}
		points.forEach((p) -> {
			this.points.put(p.getName(), p);
		});
	}
	
	public void addPoint(PointObject point) {
		points.put(point.getName(), point);
	}
	
	public Collection<ActionObject> getActions() {
		return actions.values();
	}
	
	public void setActions(Collection<ActionObject> actions) {
		
		if (actions == null) {
			return;
		}
		actions.forEach(a -> {
			this.actions.put(a.getName(), a);
		});
	}
	
	public void addAction(ActionObject action) {
		actions.put(action.getName(), action);
	}
	
	@JsonIgnore
	public Map<String, PointObject> getPointsMap() {
		return points;
	}
	
	@JsonIgnore
	public void setPointsMap(Map<String, PointObject> points) {
		this.points = points;
	}
	
	@JsonIgnore
	public Map<String, ActionObject> getActionsMap() {
		return actions;
	}
	
	@JsonIgnore
	public void setActionsMap(Map<String, ActionObject> actions) {
		this.actions = actions;
	}
	
	public String getErrorInfoName() {
		return errorInfoName;
	}
	
	public void setErrorInfoName(String errorInfoName) {
		this.errorInfoName = errorInfoName;
	}
	
	public EnumObject getErrorInfo() {
		return errorInfo;
	}
	
	public void setErrorInfo(EnumObject errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public Collection<ReferenceObject> getReferences() {
		return references.values();
	}
	
	public void setReferences(Collection<ReferenceObject> references) {
		if (references == null) {
			return;
		}
		references.forEach(r -> {
			this.references.put(r.getName(), r);
		});
	}
	
	@JsonIgnore
	public Map<String, ReferenceObject> getReferencesMap() {
		return references;
	}
	
	@JsonIgnore
	public void setReferencesMap(Map<String, ReferenceObject> refMap) {
		this.references = refMap;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	@JsonProperty("extends")
	public String getExtendsSchema() {
		return extendsSchema;
	}
	
	public void setExtendsSchema(String extendsSchema) {
		this.extendsSchema = extendsSchema;
	}
	
	
	public boolean isDelete() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete=delete;
	}
}
