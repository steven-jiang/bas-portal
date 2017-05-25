package com.kii.bas.service.schema.viewentity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import com.kii.bas.service.schema.entity.DescriptionObj;
import com.kii.bas.service.schema.entity.SchemaObj;

public class FullDescriptionObj {
	
	private DescriptionObj descObj;
	
	private SchemaObj schemaObj;
	
	public FullDescriptionObj(SchemaObj schema, DescriptionObj desc) {
		
		this.descObj = desc;
		this.schemaObj = schema;
	}
	
	@JsonUnwrapped
	public DescriptionObj getDescObj() {
		return descObj;
	}
	
	
	@JsonUnwrapped
	public SchemaObj getSchemaObj() {
		return schemaObj;
	}
	
}
