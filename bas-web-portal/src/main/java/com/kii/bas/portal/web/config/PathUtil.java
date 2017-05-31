package com.kii.bas.portal.web.config;

import java.util.function.BiFunction;

public class PathUtil {
	
	public static final String BASE_SCHEMA_RESOURCE_PATH = "/deviceSchema/{name}/resources/";
	
	
	public static final BiFunction<String, String, String> function = (n, k) -> {
		
		return "/deviceSchema/" + n + "/resources/" + k;
		
	};
}
