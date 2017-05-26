package com.kii.bas.portal.demo;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyWebEnvLoader {
	
	
	public static void main(String[] args) throws Exception {
		
		System.setProperty("spring.profile", "local");

		Server server = new Server(7070);
		
		WebAppContext webapp = new WebAppContext();
		
		webapp.setContextPath("/bas");
		
		
		File warFile = new File("bas-web-portal/src/main/webapp/");
		
		webapp.setWar(warFile.getAbsolutePath());
		
		server.setHandler(webapp);
		
		server.start();
		server.join();
	}
}
