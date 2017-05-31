package com.kii.bas.portal.web.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.kii.bas.portal.web.config.PathUtil;
import com.kii.bas.service.schema.ThingSchemaService;

@Controller
@RequestMapping(path = {PathUtil.BASE_SCHEMA_RESOURCE_PATH}, consumes = {MediaType.ALL_VALUE}, produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SchemaResourceController {
	
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private ThingSchemaService service;
	
	@PostMapping("/{bindName}")
	public String handleFormUpload(@RequestParam("description") String description,
								   @RequestParam("file") MultipartFile file,
								   @PathVariable("name") String schemaName,
								   @PathVariable("bindName") String name) {
		
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// store the bytes somewhere
			return "redirect:uploadSuccess";
		}
		
		return "redirect:uploadFailure";
	}
	
	
	@ResponseBody
	@RequestMapping(path = {"/{bindName}"}, method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE})
	public ResponseEntity getResourceFromPoint(@PathVariable("bindName") String name, @PathVariable("name") String schemaName, WebRequest request) {
		
		return null;
//		FullResourceEntry entry=service.getDeviceResourceByName(PathUtil.function,schemaName).getEntityMap().get(name);
//
//
//		if (request.checkNotModified(entry.getResourceEntry().get.getTime())) {
//
//			ResponseEntity entity = ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
//
//			return entity;
//		}
//
//
//
//		Resource resource = loader.getResource("file:///Users/steven/workspace/bas-backend/bas-web-portal/src/test/resources/demo.png");
//
//
//		ResponseEntity<Resource> entity = ResponseEntity
//				.ok()
//				.contentLength(entry.getSize())
//				.contentType(entry.getMediaType())
//				.lastModified(entry.getLastModify().getTime())
//				.body(resource);
//		return entity;
	}
}
