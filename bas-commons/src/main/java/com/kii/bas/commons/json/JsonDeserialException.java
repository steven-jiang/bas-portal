package com.kii.bas.commons.json;

import java.io.IOException;

public class JsonDeserialException extends RuntimeException {
	
	public JsonDeserialException(IOException e) {
		
		super(e);
		
	}
}
