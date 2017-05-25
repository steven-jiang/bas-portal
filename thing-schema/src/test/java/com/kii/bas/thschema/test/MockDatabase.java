package com.kii.bas.thschema.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.CreateViewOptions;

public class MockDatabase implements MongoDatabase {
	
	
	private Map<String, MockCollection> store = new HashMap<>();
	
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public CodecRegistry getCodecRegistry() {
		return null;
	}
	
	@Override
	public ReadPreference getReadPreference() {
		return null;
	}
	
	@Override
	public WriteConcern getWriteConcern() {
		return null;
	}
	
	@Override
	public ReadConcern getReadConcern() {
		return null;
	}
	
	@Override
	public MongoDatabase withCodecRegistry(CodecRegistry codecRegistry) {
		return null;
	}
	
	@Override
	public MongoDatabase withReadPreference(ReadPreference readPreference) {
		return null;
	}
	
	@Override
	public MongoDatabase withWriteConcern(WriteConcern writeConcern) {
		return null;
	}
	
	@Override
	public MongoDatabase withReadConcern(ReadConcern readConcern) {
		return null;
	}
	
	@Override
	public MongoCollection<Document> getCollection(String collectionName) {
		return store.computeIfAbsent(collectionName, (k) -> {
			return new MockCollection();
		});
	}
	
	@Override
	public <TDocument> MongoCollection<TDocument> getCollection(String collectionName, Class<TDocument> tDocumentClass) {
		return null;
	}
	
	@Override
	public Document runCommand(Bson command) {
		return null;
	}
	
	@Override
	public Document runCommand(Bson command, ReadPreference readPreference) {
		return null;
	}
	
	@Override
	public <TResult> TResult runCommand(Bson command, Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public <TResult> TResult runCommand(Bson command, ReadPreference readPreference, Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public void drop() {
		
	}
	
	@Override
	public MongoIterable<String> listCollectionNames() {
		return null;
	}
	
	@Override
	public ListCollectionsIterable<Document> listCollections() {
		return null;
	}
	
	@Override
	public <TResult> ListCollectionsIterable<TResult> listCollections(Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public void createCollection(String collectionName) {
		
	}
	
	@Override
	public void createCollection(String collectionName, CreateCollectionOptions createCollectionOptions) {
		
	}
	
	@Override
	public void createView(String viewName, String viewOn, List<? extends Bson> pipeline) {
		
	}
	
	@Override
	public void createView(String viewName, String viewOn, List<? extends Bson> pipeline, CreateViewOptions createViewOptions) {
		
	}
}
