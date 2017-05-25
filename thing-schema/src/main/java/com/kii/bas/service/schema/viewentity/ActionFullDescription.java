package com.kii.bas.service.schema.viewentity;

import java.util.HashMap;
import java.util.Map;

import com.kii.bas.service.schema.entity.ActionDescription;
import com.kii.bas.service.schema.entity.ActionObject;
import com.kii.bas.service.schema.entity.DescriptionObj;

public class ActionFullDescription {
	
	private String name;
	
	private String description;
	
	private Map<String, FullDescriptionObj> fields = new HashMap<>();
	
	
	public ActionFullDescription(ActionObject action, ActionDescription desc) {
		
		this.name = action.getName();
		this.description = desc.getDescription();
		
		action.getFields().forEach((k, v) -> {
			
			DescriptionObj descObj = desc.getFields().get(k);
			
			FullDescriptionObj fullObj = new FullDescriptionObj(v, descObj);
			
			fields.put(k, fullObj);
		});
		
	}
	
	public ActionFullDescription(ActionObject action, ActionFullDescription desc) {
		
		this.name = action.getName();
		this.description = desc.getDescription();
		
		action.getFields().forEach((k, v) -> {
			
			DescriptionObj descObj = desc.getFields().get(k).getDescObj();
			
			FullDescriptionObj fullObj = new FullDescriptionObj(v, descObj);
			
			fields.put(k, fullObj);
		});
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	
	public Map<String, FullDescriptionObj> getFields() {
		return fields;
	}
}
