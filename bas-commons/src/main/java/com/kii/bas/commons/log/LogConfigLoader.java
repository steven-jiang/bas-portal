package com.kii.bas.commons.log;

import org.apache.logging.log4j.core.config.Configurator;

public class LogConfigLoader {
	
	
	public LogConfigLoader(String path) {
		
		
		Configurator.initialize("kii-bas", this.getClass().getClassLoader(), path);
		
	}
}
