package com.kii.bas.service.schema.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.kii.bas.commons.store.mongo.StoreEntity;


public class DeviceDescription extends StoreEntity {
	
	private String location = Locale.SIMPLIFIED_CHINESE.toString();
	
	private String deviceType;
	
	private String description;
	
	private Map<String, Object> additionInfos = new HashMap<>();
	
	private Map<String, String> errorInfoDict = new HashMap<>();
	
	private Map<String, PointDescription> pointsMap = new HashMap<>();
	
	private Map<String, ActionDescription> actionsMap = new HashMap<>();
	
	private boolean delete;
	
	public DeviceDescription() {
		
	}
	
	public DeviceDescription(DeviceSchema schema) {
		
		description = schema.getDeviceType();
		deviceType = schema.getDeviceType();
		
		for (Object obj : schema.getErrorInfo().getEnumValues()) {
			String val = String.valueOf(obj);
			errorInfoDict.put(val, val);
		}
		;
		
		schema.getPointsMap().forEach((k, v) -> {
			pointsMap.put(k, new PointDescription(v));
		});
		
		
		schema.getActionsMap().forEach((k, v) -> {
			actionsMap.put(k, new ActionDescription(v));
		});
		
		
	}
	
	public DeviceDescription(DeviceDescription schema) {
		this.pointsMap = new HashMap<>(schema.pointsMap);
		this.actionsMap = new HashMap<>(schema.actionsMap);
		this.location = schema.location;
		this.errorInfoDict = new HashMap<>(schema.errorInfoDict);
		this.deviceType = schema.deviceType;
		this.description = schema.description;
		this.additionInfos = schema.additionInfos;
	}
	
	
	public Map<String, Object> getAdditionInfos() {
		return additionInfos;
	}
	
	public void setAdditionInfos(Map<String, Object> additionInfos) {
		this.additionInfos = additionInfos;
	}
	
	public DeviceDescription combineSuperDescription(DeviceDescription desc) {
		
		desc.pointsMap.forEach((k, p) -> {
			pointsMap.computeIfAbsent(k, (name) -> {
				return p;
			});
		});
		
		desc.actionsMap.forEach((k, a) -> {
			actionsMap.computeIfAbsent(k, (name) -> {
				return a;
			});
		});
		
		if (errorInfoDict == null) {
			errorInfoDict = desc.errorInfoDict;
		}
		
		if (description == null) {
			this.description = desc.getDescription();
		}
		
		desc.additionInfos.forEach((k, a) -> {
			additionInfos.computeIfAbsent(k, (name) -> {
				return a;
			});
		});
		return this;
	}
	
	public String getFullDescriptionName() {
		return deviceType + "_" + location;
	}
	
	
	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Collection<PointDescription> getPoints() {
		return pointsMap.values();
	}
	
	public void setPoints(Collection<PointDescription> points) {
		
		if (points == null) {
			return;
		}
		;
		
		points.forEach(p -> pointsMap.put(p.getName(), p));
	}
	
	public void addPoint(PointDescription point) {
		pointsMap.put(point.getName(), point);
	}
	
	public Collection<ActionDescription> getActions() {
		return actionsMap.values();
	}
	
	public void setActions(Collection<ActionDescription> actions) {
		
		if (actions == null) {
			return;
		}
		actions.forEach(a -> actionsMap.put(a.getName(), a));
	}
	
	public void addAction(ActionDescription action) {
		actionsMap.put(action.getName(), action);
	}
	
	public Map<String, String> getErrorInfoDict() {
		return errorInfoDict;
	}
	
	public void setErrorInfoDict(Map<String, String> errorInfoDict) {
		this.errorInfoDict = errorInfoDict;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public boolean isDelete() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete=delete;
	}
	
}
