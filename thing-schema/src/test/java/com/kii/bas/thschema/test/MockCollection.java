package com.kii.bas.thschema.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.CursorType;
import com.mongodb.Function;
import com.mongodb.MongoNamespace;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.DeleteOptions;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.RenameCollectionOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MockCollection implements MongoCollection<Document> {
	
	
	private Map<Object, Document> storeMap = new HashMap<>();
	
	@Override
	public MongoNamespace getNamespace() {
		return null;
	}
	
	@Override
	public Class<Document> getDocumentClass() {
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
	public <NewTDocument> MongoCollection<NewTDocument> withDocumentClass(Class<NewTDocument> clazz) {
		return null;
	}
	
	@Override
	public MongoCollection<Document> withCodecRegistry(CodecRegistry codecRegistry) {
		return null;
	}
	
	@Override
	public MongoCollection<Document> withReadPreference(ReadPreference readPreference) {
		return null;
	}
	
	@Override
	public MongoCollection<Document> withWriteConcern(WriteConcern writeConcern) {
		return null;
	}
	
	@Override
	public MongoCollection<Document> withReadConcern(ReadConcern readConcern) {
		return null;
	}
	
	@Override
	public long count() {
		return 0;
	}
	
	@Override
	public long count(Bson filter) {
		return 0;
	}
	
	@Override
	public long count(Bson filter, CountOptions options) {
		return 0;
	}
	
	@Override
	public <TResult> DistinctIterable<TResult> distinct(String fieldName, Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public <TResult> DistinctIterable<TResult> distinct(String fieldName, Bson filter, Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public FindIterable<Document> find() {
		
		return new MockFindIterable(storeMap.values());
		
	}
	
	@Override
	public <TResult> FindIterable<TResult> find(Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public FindIterable<Document> find(Bson filter) {
		
		BasicDBObject db = (BasicDBObject) filter;
		
		String id = (String) db.get("_id");
		if (id == null) {
			return new MockFindIterable(storeMap.values());
		} else {
			return new MockFindIterable(storeMap.get(db.get("_id")));
			
		}
	}
	
	@Override
	public <TResult> FindIterable<TResult> find(Bson filter, Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public AggregateIterable<Document> aggregate(List<? extends Bson> pipeline) {
		return null;
	}
	
	@Override
	public <TResult> AggregateIterable<TResult> aggregate(List<? extends Bson> pipeline, Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public MapReduceIterable<Document> mapReduce(String mapFunction, String reduceFunction) {
		return null;
	}
	
	@Override
	public <TResult> MapReduceIterable<TResult> mapReduce(String mapFunction, String reduceFunction, Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public BulkWriteResult bulkWrite(List<? extends WriteModel<? extends Document>> requests) {
		return null;
	}
	
	@Override
	public BulkWriteResult bulkWrite(List<? extends WriteModel<? extends Document>> requests, BulkWriteOptions options) {
		return null;
	}
	
	@Override
	public void insertOne(Document document) {
		
		storeMap.put(document.get("_id"), document);
	}
	
	@Override
	public void insertOne(Document document, InsertOneOptions options) {
		
	}
	
	@Override
	public void insertMany(List<? extends Document> documents) {
		
	}
	
	@Override
	public void insertMany(List<? extends Document> documents, InsertManyOptions options) {
		
	}
	
	@Override
	public DeleteResult deleteOne(Bson filter) {
		return null;
	}
	
	@Override
	public DeleteResult deleteOne(Bson filter, DeleteOptions options) {
		return null;
	}
	
	@Override
	public DeleteResult deleteMany(Bson filter) {
		return null;
	}
	
	@Override
	public DeleteResult deleteMany(Bson filter, DeleteOptions options) {
		return null;
	}
	
	@Override
	public UpdateResult replaceOne(Bson filter, Document replacement) {
		return null;
	}
	
	@Override
	public UpdateResult replaceOne(Bson filter, Document replacement, UpdateOptions updateOptions) {
		return null;
	}
	
	@Override
	public UpdateResult updateOne(Bson filter, Bson update) {
		return null;
	}
	
	@Override
	public UpdateResult updateOne(Bson filter, Bson update, UpdateOptions updateOptions) {
		return null;
	}
	
	@Override
	public UpdateResult updateMany(Bson filter, Bson update) {
		return null;
	}
	
	@Override
	public UpdateResult updateMany(Bson filter, Bson update, UpdateOptions updateOptions) {
		return null;
	}
	
	@Override
	public Document findOneAndDelete(Bson filter) {
		return null;
	}
	
	@Override
	public Document findOneAndDelete(Bson filter, FindOneAndDeleteOptions options) {
		
		return null;
		
	}
	
	@Override
	public Document findOneAndReplace(Bson filter, Document replacement) {
		return null;
	}
	
	@Override
	public Document findOneAndReplace(Bson filter, Document replacement, FindOneAndReplaceOptions options) {
		BasicDBObject db = (BasicDBObject) filter;
		storeMap.put(db.get("_id"), replacement);
		
		return replacement;
	}
	
	@Override
	public Document findOneAndUpdate(Bson filter, Bson update) {
		return null;
	}
	
	@Override
	public Document findOneAndUpdate(Bson filter, Bson update, FindOneAndUpdateOptions options) {
		return null;
	}
	
	@Override
	public void drop() {
		
	}
	
	@Override
	public String createIndex(Bson keys) {
		return null;
	}
	
	@Override
	public String createIndex(Bson keys, IndexOptions indexOptions) {
		return null;
	}
	
	@Override
	public List<String> createIndexes(List<IndexModel> indexes) {
		return null;
	}
	
	@Override
	public ListIndexesIterable<Document> listIndexes() {
		return null;
	}
	
	@Override
	public <TResult> ListIndexesIterable<TResult> listIndexes(Class<TResult> tResultClass) {
		return null;
	}
	
	@Override
	public void dropIndex(String indexName) {
		
	}
	
	@Override
	public void dropIndex(Bson keys) {
		
	}
	
	@Override
	public void dropIndexes() {
		
	}
	
	@Override
	public void renameCollection(MongoNamespace newCollectionNamespace) {
		
	}
	
	@Override
	public void renameCollection(MongoNamespace newCollectionNamespace, RenameCollectionOptions renameCollectionOptions) {
		
	}
	
	
	private class MockFindIterable implements FindIterable<Document> {
		
		
		private Collection<Document> collect = new ArrayList<>();
		
		public MockFindIterable(Collection<Document> collect) {
			this.collect = collect;
		}
		
		public MockFindIterable(Document doc) {
			this.collect.add(doc);
		}
		
		@Override
		public FindIterable<Document> filter(Bson filter) {
			return null;
		}
		
		@Override
		public FindIterable<Document> limit(int limit) {
			return null;
		}
		
		@Override
		public FindIterable<Document> skip(int skip) {
			return null;
		}
		
		@Override
		public FindIterable<Document> maxTime(long maxTime, TimeUnit timeUnit) {
			return null;
		}
		
		@Override
		public FindIterable<Document> maxAwaitTime(long maxAwaitTime, TimeUnit timeUnit) {
			return null;
		}
		
		@Override
		public FindIterable<Document> modifiers(Bson modifiers) {
			return null;
		}
		
		@Override
		public FindIterable<Document> projection(Bson projection) {
			return null;
		}
		
		@Override
		public FindIterable<Document> sort(Bson sort) {
			return null;
		}
		
		@Override
		public FindIterable<Document> noCursorTimeout(boolean noCursorTimeout) {
			return null;
		}
		
		@Override
		public FindIterable<Document> oplogReplay(boolean oplogReplay) {
			return null;
		}
		
		@Override
		public FindIterable<Document> partial(boolean partial) {
			return null;
		}
		
		@Override
		public FindIterable<Document> cursorType(CursorType cursorType) {
			return null;
		}
		
		@Override
		public FindIterable<Document> batchSize(int batchSize) {
			return null;
		}
		
		@Override
		public FindIterable<Document> collation(Collation collation) {
			return null;
		}
		
		@Override
		public MongoCursor<Document> iterator() {
			return null;
		}
		
		@Override
		public Document first() {
			return collect.iterator().next();
		}
		
		@Override
		public <U> MongoIterable<U> map(Function<Document, U> mapper) {
			return null;
		}
		
		@Override
		public void forEach(Block<? super Document> block) {
			
		}
		
		@Override
		public void forEach(Consumer<? super Document> action) {
			
			collect.forEach(action);
		}
		
		@Override
		public <A extends Collection<? super Document>> A into(A target) {
			return null;
		}
	}
	
	;
}
