package com.kii.bas.portal.web.controller;


import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
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

import com.kii.bas.portal.web.entity.ResourceEntry;

@Controller
@RequestMapping(path = {"/schema/resources"}, consumes = {MediaType.ALL_VALUE}, produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SchemaResourceController {
	
	
	@Autowired
	private ResourceLoader loader;
	
	@PostMapping("/{bindName}")
	public String handleFormUpload(@RequestParam("description") String description,
								   @RequestParam("file") MultipartFile file) {
		
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
	@RequestMapping(path = {"/{bindName}"}, method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE}, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity getResourceFromPoint(@PathVariable("bindName") String name, WebRequest request, HttpServletResponse response) {
		
		
		ResourceEntry entry = new ResourceEntry();
		entry.setMediaType(MediaType.IMAGE_PNG_VALUE);
//		entry.setPath(new File("/Users/steven/workspace/bas-backend/bas-web-portal/src/test/resources/demo.png"));
		entry.setSize(27945l);
		entry.setLastModify(new Date());
		
		
		if (request.checkNotModified(entry.getLastModify().getTime())) {
			
			ResponseEntity entity = ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
			
			return entity;
		}
		
		Resource resource = loader.getResource("file:///Users/steven/workspace/bas-backend/bas-web-portal/src/test/resources/demo.png");
		
		
		ResponseEntity<Resource> entity = ResponseEntity
				.ok()
				.contentLength(entry.getSize())
				.contentType(entry.getMediaType())
				.lastModified(entry.getLastModify().getTime())
				.body(resource);
		return entity;
	}
}
