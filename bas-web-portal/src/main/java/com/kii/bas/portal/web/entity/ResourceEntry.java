package com.kii.bas.portal.web.entity;

import java.io.File;
import java.util.Date;

import org.springframework.http.MediaType;

public class ResourceEntry {
	
	private Date lastModify;
	
	private String mediaType;
	
	private long size;
	
	private File path;
	
	
	public Date getLastModify() {
		return lastModify;
	}
	
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}
	
	public MediaType getMediaType() {
		return MediaType.parseMediaType(mediaType);
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
	
	public File getPath() {
		return path;
	}
	
	public void setPath(File path) {
		this.path = path;
	}
}
