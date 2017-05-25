package com.kii.bas.commons.store.mongo;

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
	
	@Bean
	public MongoDatabase getClient() {
		
		MongoClientURI connectionString = new MongoClientURI(url);
		
		MongoClient mongoClient = new MongoClient(connectionString);
		
		return mongoClient.getDatabase(dbName);
	}
	
	@Bean
	public com.mongodb.async.client.MongoDatabase getAsyncClient() {
		com.mongodb.async.client.MongoClient mongoClient = com.mongodb.async.client.MongoClients.create(url);
		
		return mongoClient.getDatabase(dbName);
	}
}
