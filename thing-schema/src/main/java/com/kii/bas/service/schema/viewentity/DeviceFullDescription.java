package com.kii.bas.service.schema.viewentity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kii.bas.service.schema.entity.ActionObject;
import com.kii.bas.service.schema.entity.DeviceDescription;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.entity.EnumObject;
import com.kii.bas.service.schema.entity.PointObject;
import com.kii.bas.service.schema.entity.ReferenceObject;

public class DeviceFullDescription {
	
	
	private static DeviceFullDescription deletedInst = new DeviceFullDescription();
	
	static {
		
		deletedInst.setDeleted(true);
	}
	
	private Map<String, PointFullDescription> pointsMap = new HashMap<>();
	private Map<String, ActionFullDescription> actionsMap = new HashMap<>();
	private Map<String, ReferenceObject> references = new HashMap<>();
	private String errorInfoName;
	private EnumObject errorInfo;
	private Map<String, String> errorInfoDict;
	private String deviceType;
	private String location;
	private String description;
	private boolean isDeleted = false;
	
	private DeviceFullDescription() {
		
	}
	
	public DeviceFullDescription(DeviceSchema schema, DeviceDescription description) {
		
		this.errorInfo = schema.getErrorInfo();
		this.errorInfoName = schema.getErrorInfoName();
		this.deviceType = schema.getDeviceType();
		
		this.errorInfoDict = description.getErrorInfoDict();
		this.location = description.getLocation();
		this.description = description.getDescription();
		
		this.references = schema.getReferencesMap();
		
		description.getPoints().forEach((v) -> {
			
			PointObject point = schema.getPointsMap().get(v.getName());
			pointsMap.put(v.getName(), new PointFullDescription(point, v));
		});
		
		description.getActions().forEach((v) -> {
			
			ActionObject point = schema.getActionsMap().get(v.getName());
			actionsMap.put(v.getName(), new ActionFullDescription(point, v));
		});
		
	}
	
	
	public DeviceFullDescription(DeviceSchema schema, DeviceDescription description, DeviceFullDescription superDesc) {
		
		this(schema, description);
		
		if (this.errorInfoDict == null) {
			this.errorInfoDict = superDesc.getErrorInfoDict();
		}
		
		if (StringUtils.isEmpty(this.description)) {
			this.description = superDesc.getDescription();
		}
		if (StringUtils.isEmpty(this.location)) {
			this.location = superDesc.getLocation();
		}
		
		superDesc.pointsMap.forEach((k, v) -> {
			pointsMap.computeIfAbsent(k, (key) -> {
				return new PointFullDescription(schema.getPointsMap().get(k), v);
			});
		});
		
		superDesc.actionsMap.forEach((k, v) -> {
			actionsMap.computeIfAbsent(k, (key) -> {
				return new ActionFullDescription(schema.getActionsMap().get(k), v);
			});
		});
		
		
	}
	
	public static DeviceFullDescription getDeletedInstance() {
		
		return deletedInst;
	}
	
	public Collection<PointFullDescription> getPoints() {
		return pointsMap.values();
	}
	
	public Collection<ActionFullDescription> getActions() {
		return actionsMap.values();
	}
	
	public Collection<ReferenceObject> getReferences() {
		return references.values();
	}
	
	public String getErrorInfoName() {
		return errorInfoName;
	}
	
	public EnumObject getErrorInfo() {
		return errorInfo;
	}
	
	public Map<String, String> getErrorInfoDict() {
		return errorInfoDict;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}
}
