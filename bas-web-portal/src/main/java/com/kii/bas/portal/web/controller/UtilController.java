package com.kii.bas.portal.web.controller;

import javax.servlet.http.HttpServletRequest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UtilController {
	
	@Value("${spring.profile}")
	private String profile;
	
	@RequestMapping(value = "/info", method = {RequestMethod.GET}, consumes = {"*"})
	public Map<String, String> info(HttpServletRequest httpRequest) {
		Map<String, String> map = new HashMap<>();
		InputStream manifestStream = httpRequest.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
		try {
			Manifest manifest = new Manifest(manifestStream);
			Attributes attributes = manifest.getMainAttributes();
			String impVersion = attributes.getValue("Implementation-Version");
			String impTitle = attributes.getValue("Implementation-Title");
			String impTimestamp = attributes.getValue("Implementation-Timestamp");
			map.put("Version", impVersion);
			map.put("Title", impTitle);
			map.put("Date", impTimestamp);
			map.put("profile", profile);
		} catch (Exception ex) {
			//log.warn("Error while reading version: " + ex.getMsgInText());
		}
		return map;
	}
}
