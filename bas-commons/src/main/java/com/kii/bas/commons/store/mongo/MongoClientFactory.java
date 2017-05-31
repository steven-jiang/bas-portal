package com.kii.bas.commons.store.mongo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


public class MongoClientFactory {
	
	
	@Value("${store.mongoDB.connectUrl}")
	private String url;
	
	@Value("${store.mongoDB.dbName}")
	private String dbName;
	
	
	private MongoClient mongoClient;
	
	private com.mongodb.async.client.MongoClient asyncClient;
	
	
	@PostConstruct
	public void init() {
		
		
		MongoClientURI connectionString = new MongoClientURI(url);
		
		
		mongoClient = new MongoClient(connectionString);
		
		asyncClient = com.mongodb.async.client.MongoClients.create(url);
		
	}
	
	
	@PreDestroy
	public void close() {
		
		mongoClient.close();
		
		asyncClient.close();
	}
	
	@Bean
	public MongoDatabase getClient() {
		
		
		return mongoClient.getDatabase(dbName);
	}
	
	@Bean
	public com.mongodb.async.client.MongoDatabase getAsyncClient() {
		
		return asyncClient.getDatabase(dbName);
	}
}
