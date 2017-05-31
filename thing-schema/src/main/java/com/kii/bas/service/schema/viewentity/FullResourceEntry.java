package com.kii.bas.service.schema.viewentity;

import java.util.function.BiFunction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.kii.bas.service.schema.entity.ResourceEntry;

public class FullResourceEntry {
	
	private ResourceEntry entry;
	
	private String fullResourceUrl;
	
	public FullResourceEntry() {
		
	}
	
	public FullResourceEntry(ResourceEntry entry) {
		
		this.entry = entry;
	}
	
	
	public String getMediaType() {
		return entry.getMediaType();
	}
	
	public long getSize() {
		return entry.getSize();
	}
	
	public void fillFullUrl(BiFunction<String, String, String> baseUrl, String name, String key) {
		
		fullResourceUrl = baseUrl.apply(name, key);
	}
	
	public String getFullResourceUrl() {
		return fullResourceUrl;
	}
	
	@JsonIgnore
	public ResourceEntry.ResourceType getType() {
		return entry.getType();
	}
	
}
