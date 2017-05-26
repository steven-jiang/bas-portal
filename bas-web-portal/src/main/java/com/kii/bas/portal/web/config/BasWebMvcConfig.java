package com.kii.bas.portal.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.kii.bas.portal.web.controller.SysNoticeHandler;


@EnableWebMvc
@EnableWebSocket
@ComponentScan(basePackages = {"com.kii.bas.portal.web"})
public class BasWebMvcConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	
	private final ObjectMapper mapper;
	
	@Autowired
	private SysNoticeHandler handler;
	
	@Autowired
	private AsyncTaskExecutor myExecutor;
	
	public BasWebMvcConfig() {
		
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		
		
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		mapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);
		
		
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		
		registry.addHandler(handler, "/socket/sys/monitor")
				.setAllowedOrigins("*");
	}
	
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		
		return resolver;
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		converters.add(new ResourceHttpMessageConverter());
		
		converters.add(new StringHttpMessageConverter());
		
		converters.add(new ByteArrayHttpMessageConverter());
		
		converters.add(createJsonMessageConverter());
		
		super.configureMessageConverters(converters);
		
	}
	
	private HttpMessageConverter<Object> createJsonMessageConverter() {
		
		MappingJackson2HttpMessageConverter jsonMarshaller = new MappingJackson2HttpMessageConverter();
		jsonMarshaller.setObjectMapper(mapper);
		
		return jsonMarshaller;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {


//		registry.addInterceptor(authInterceptor).addPathPatterns("/**");
		
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "PUT", "POST", "DELETE", "HEAD").allowCredentials(false);
	}
	
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		
		configurer.setDefaultTimeout(60 * 1000l);
		configurer.setTaskExecutor(myExecutor);
	}
}
