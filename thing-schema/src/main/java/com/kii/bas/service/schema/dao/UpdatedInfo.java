package com.kii.bas.service.schema.dao;

import java.util.HashSet;
import java.util.Set;

import com.kii.bas.service.schema.entity.DeviceDescription;
import com.kii.bas.service.schema.entity.DeviceSchema;


public class UpdatedInfo {
	
	private Set<String> updated = new HashSet<>();
	
	private Set<String> deleted = new HashSet<>();
	
	
	public void addEntity(DeviceSchema t, String id) {
		if (t.isDelete()) {
			deleted.add(id);
		} else {
			updated.add(id);
		}
	}
	
	
	public void addEntity(DeviceDescription t, String id) {
		if (t.isDelete()) {
			deleted.add(id);
		} else {
			updated.add(id);
		}
	}
	
	public Set<String> getUpdated() {
		return updated;
	}
	
	public void setUpdated(Set<String> updated) {
		this.updated = updated;
	}
	
	public Set<String> getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Set<String> deleted) {
		this.deleted = deleted;
	}
}
