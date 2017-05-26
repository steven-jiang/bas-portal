package com.kii.bas.portal.web.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping(path = {"/schema/resources"}, consumes = {MediaType.ALL_VALUE}, produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SchemaResourceController {
	
	
	public void addResourceToPoint() {
		
	}
	
	
	@RequestMapping(path = {"/{bindName}"}, method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE}, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE})
	public StreamingResponseBody getResourceFromPoint(@PathVariable("bindName") String name, WebRequest request) {
		
		
		long lastModified = // 1. application-specific calculation
		
		if (request.checkNotModified(lastModified)) {
			// 2. shortcut exit - no further processing necessary
			return null;
		}
		
		
		return outptream -> {
			
			File file = new File("?");
			try (FileInputStream input = new FileInputStream(file)
			) {
				StreamUtils.copy(new FileInputStream(file), outptream);
			} catch (IOException e) {
				
			}
		};
	}
}
