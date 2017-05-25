package com.kii.bas.commons.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonSerialException extends RuntimeException {
	
	public JsonSerialException(JsonProcessingException e) {
		
		super(e);
		
	}
	
	
}
