package com.kii.bas.portal.web.controller;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kii.bas.service.schema.ThingSchemaService;
import com.kii.bas.service.schema.entity.DeviceDescription;
import com.kii.bas.service.schema.entity.DeviceSchema;
import com.kii.bas.service.schema.viewentity.DeviceFullDescription;

@RestController
@RequestMapping(path = {"/schema"}, consumes = {MediaType.ALL_VALUE}, produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SchemaController {
	
	
	private static Locale defaultLocale = Locale.SIMPLIFIED_CHINESE;
	@Autowired
	private ThingSchemaService service;
	
	@RequestMapping(value = "/fullDeviceDescription/all", method = {RequestMethod.GET})
	public Map<String, DeviceFullDescription> getAllSchema() {
		
		return service.getFullDescriptionMap(defaultLocale);
	}
	
	@RequestMapping(value = "/fullDeviceDescription/modify/{timeStamp}", method = {RequestMethod.GET})
	public Map<String, DeviceFullDescription> getModifySchema(@PathVariable("timeStamp") long timeStamp) {
		
		Date date = new Date(timeStamp);
		
		return service.getUpdatedFullDescriptionMap(date, defaultLocale);
	}
	
	@RequestMapping(value = "/fullDeviceSchema/name/{name}", method = {RequestMethod.GET})
	public DeviceSchema getFullDeviceDescription(@PathVariable("name") String name) {
		
		return service.getCompleteDeviceSchema(name);
	}
	
	@RequestMapping(value = "/fullDeviceDescription/name/{name}", method = {RequestMethod.GET})
	public DeviceFullDescription getFullDeviceSchema(@PathVariable("name") String name) {
		
		return service.getFullDescriptionMap(defaultLocale).get(name);
	}
	
	@RequestMapping(value = "/deviceSchema/{name}", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void putDeviceSchema(DeviceSchema schema, @PathVariable("name") String name) {
		
		schema.setDeviceType(name);
		service.putDeviceType(schema);
	}
	
	@RequestMapping(value = "/deviceSchema/{name}/description", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void putDeviceDescriptionSchema(DeviceDescription schema, @PathVariable("name") String name) {
		
		schema.setDeviceType(name);
		
		service.putDeviceDescription(schema);
	}
	
	@RequestMapping(value = "/deviceSchema/{name}", method = {RequestMethod.GET})
	public DeviceSchema getDeviceSchema(@PathVariable("name") String name) {
		
		return service.getOriginDeviceSchema(name);
	}
	
	@RequestMapping(value = "/deviceSchema/{name}/description", method = {RequestMethod.GET})
	public DeviceDescription getDeviceDescriptionSchema(@PathVariable("name") String name) {
		
		return service.getDeviceDescription(name, defaultLocale);
	}
	
	
	@RequestMapping(value = "/deviceSchema/{name}", method = {RequestMethod.DELETE})
	public void deleteDeviceSchema(@PathVariable("name") String name) {
		
		service.deleteSchema(name);
	}
	
	@RequestMapping(value = "/deviceSchema/{name}/description", method = {RequestMethod.DELETE})
	public void deleteDeviceDescriptionSchema(@PathVariable("name") String name) {
		
		service.deleteDescription(name, defaultLocale.toString());
	}
	
	
	@RequestMapping(value = "/deviceSchema/{name}", method = {RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void patchDeviceSchema(@PathVariable("name") String name, @RequestBody DeviceSchema schema) {
		
		service.patchDeviceType(schema, name);
	}
	
	@RequestMapping(value = "/deviceSchema/{name}/description", method = {RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void patchDeviceDescriptionSchema(@PathVariable("name") String name, @RequestBody DeviceDescription description) {
		
		service.patchDeviceDescription(description, name, defaultLocale.toLanguageTag());
	}
	
}
