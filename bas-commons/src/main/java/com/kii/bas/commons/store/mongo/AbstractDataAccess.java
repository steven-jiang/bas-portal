package com.kii.bas.commons.store.mongo;


import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.UpdateResult;

import com.kii.bas.commons.json.SafeObjectMapper;
import com.kii.bas.commons.store.entity.QueryPager;
import com.kii.bas.commons.store.entity.StatusType;
import com.kii.bas.commons.store.mongo.exception.MongoStoreException;
import com.kii.bas.commons.utils.TypeTools;


public abstract class AbstractDataAccess<T extends StoreEntity> {
	
	
	@Autowired
	protected SafeObjectMapper mapper;
	
	@Autowired
	private MongoDatabase database;
	
	
	private Class<T> getEntityClass() {
		return TypeTools.getGenericTypeClass(this.getClass());
	}
	
	
	private Document getDocument(Object entity) {
		
		
		String json = mapper.writeValueAsString(entity);
		
		return Document.parse(json);
	}
	
	protected abstract String getCollectName();
	
	private Document addModifyTag(Document doc) {
		
		
		BasicDBObject param = new BasicDBObject("modifyDate", false);
		
		Document newDoc = new Document();
		
		newDoc.append("$set", new BasicDBObject(doc));
		
		newDoc.append("$currentDate", param);
		
		return newDoc;
	}
	
	
	private void addAllDateTag(Document doc) {
		Date date = new Date();
		
		doc.append("createDate", date);
		doc.append("modifyDate", date);
		
	}
	
	protected MongoCollection<Document> getCollect() {
		return database.getCollection(getCollectName());
	}
	
	protected String insertEntity(T entity) {
		
		Document doc = getDocument(entity);
		doc.remove("_id");
		addAllDateTag(doc);
		
		getCollect().insertOne(doc);
		
		return doc.getObjectId("_id").toHexString();
	}
	
	
	protected void insertEntitys(List<T> entitys) {
		
		List<Document> docList = new ArrayList<>();
		entitys.forEach(e -> {
			Document doc = getDocument(e);
			doc.remove("_id");
			addAllDateTag(doc);
			
			docList.add(doc);
		});
		
		getCollect().insertMany(docList);
	}
	
	
	private Bson getIDQuery(String id) {
		if (BusinessEntity.class.isAssignableFrom(getEntityClass())) {
			return eq("_id", new ObjectId(id));
		} else {
			return eq("_id", id);
		}
		
	}
	protected T getEntityByID(String id) {
		
		FindIterable<Document> iter = getCollect().find(getIDQuery(id));
		
		Document doc = iter.first();
		
		if (doc == null) {
			return null;
		} else {
			return mapper.readValue(doc.toJson(), getEntityClass());
		}
	}
	
	protected UpdateResult updateEntity(Map<String, Object> fields, String id) {
		
		Bson filter = getIDQuery(id);
		
		Document doc = getDocument(fields);
		
		return updateOrAddEntity(doc, filter, false);
	}
	
	protected UpdateResult updateOrInsertEntity(Map<String, Object> fields, String id) {
		
		Bson filter = getIDQuery(id);
		
		Document doc = getDocument(fields);
		
		return updateOrAddEntity(doc, filter, true);
	}
	
	private UpdateResult updateOrAddEntity(Document doc, Bson filter, boolean withInsert) {
		
		doc.remove("_id");
		doc = addModifyTag(doc);
		
		return getCollect().updateOne(filter, doc, new UpdateOptions().upsert(withInsert));
		
	}
	
	protected UpdateResult updateEntity(T entity, String id) {
		
		Bson filter = getIDQuery(id);

		Document doc = getDocument(entity);
		
		return updateOrAddEntity(doc, filter, false);
		
	}
	
	protected UpdateResult updateOrInsertEntity(T entity, String id) {
		
		Bson filter = getIDQuery(id);
		
		Document doc = getDocument(entity);
		
		return updateOrAddEntity(doc, filter, true);
		
	}
	
	protected void deleteEntity(String id) {
		
		
		if (BusinessEntity.class.isAssignableFrom(getEntityClass())) {
			Bson filter = eq("_id", new ObjectId(id));
			
			Map<String, Object> fields = new HashMap<>();
			fields.put("recordStatus", StatusType.deleted);
			Document doc = getDocument(fields);
			doc = addModifyTag(doc);
			
			getCollect().updateOne(filter, doc, new UpdateOptions().upsert(false));
			
		} else {
			Bson filter = eq("_id", id);
			
			getCollect().deleteOne(filter);
		}
		
		
	}
	
	protected UpdateResult replaceEntity(T entity, String id) {
		
		entity.setId(null);
		
		Bson filter = getIDQuery(id);
		
		Document doc = getDocument(entity);
		doc.remove("_id");
		doc.put("modifyDate", new Date());
		
		UpdateResult result = getCollect().replaceOne(filter, doc, new UpdateOptions().upsert(true));
		
		if (result.getModifiedCount() == 0 && result.getMatchedCount() == 0 && result.getUpsertedId() == null) {
			throw new MongoStoreException();
		}
		
		return result;
	}
	
	
	protected BulkWriteResult batchReplaceEntity(List<T> entitys) {
		
		
		List<WriteModel<Document>> modelList = new ArrayList<>();
		
		for (T entity : entitys) {
			
			Bson filter = getIDQuery(entity.getId());
			Document doc = getDocument(entity);
			doc.put("modifyDate", new Date());
			doc.remove("_id");
			
			WriteModel<Document> model = new ReplaceOneModel<>(filter, doc, new UpdateOptions().upsert(true));
			
			modelList.add(model);
		}
		
		BulkWriteResult result = getCollect().bulkWrite(modelList);
		
		return result;
	}
	
	protected void iterateAll(Consumer<T> consumer) {
		
		BasicDBObject filter = new BasicDBObject();
		
		iterate(consumer, filter, null);
	}
	
	
	protected void iterate(Consumer<T> consumer, Bson filter, String pager) {
		
		QueryPager page = QueryPager.getInstance(pager);
		
		FindIterable<Document> iter = getCollect().find(filter);
		
		if (page != null) {
			iter = iter.skip(page.getStart()).limit(page.getSize());
			
		}
			
		iter.forEach((Consumer<? super Document>) (d) -> {
			
			T entity = mapper.readValue(d.toJson(), getEntityClass());
			consumer.accept(entity);
		});
		
	}
	
	protected <E extends BusinessEntity> List<E> getAvailableListByQuery(Bson filter, String pager) {
		
		Bson cond = and(ne("recordStatus", StatusType.deleted.name()), filter);
		
		List<E> list = new ArrayList<>();
		
		iterate((d) -> {
			list.add((E) d);
		}, cond, pager);
		
		return list;
	}
	
	protected <E extends BusinessEntity> List<E> getAvailableListByQuery(Bson filter) {
		
		return getAvailableListByQuery(filter, null);
	}
	
	

	
}
