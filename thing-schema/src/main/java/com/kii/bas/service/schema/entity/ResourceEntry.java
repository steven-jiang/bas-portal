package com.kii.bas.service.schema.entity;

public class ResourceEntry {
	
	
	public static final ResourceEntry NULL = new ResourceEntry();
	private String mediaType;
	private long size;
	private ResourceType type = ResourceType.Null;
	
	public String getMediaType() {
		return mediaType;
	}
	
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public ResourceType getType() {
		return type;
	}
	
	public void setType(ResourceType type) {
		this.type = type;
	}
	
	public static enum ResourceType {
		Resource, NotInherited, Nothing, Null;
	}
}
