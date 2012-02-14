/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hubiquitus.persistence.impl;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import org.hubiquitus.hapi.model.impl.ComplexPublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.DataResultEntry;
import org.hubiquitus.hapi.model.impl.KeyRequest;
import org.hubiquitus.hapi.model.impl.Limit;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hapi.model.impl.ParamRequest;
import org.hubiquitus.hapi.model.impl.PayloadResultEntry;
import org.hubiquitus.hapi.model.impl.SortRequest;
import org.hubiquitus.hapi.utils.impl.CommonJSonParserImpl;
import org.hubiquitus.persistence.DataBase;
import org.hubiquitus.persistence.exeption.DBException;
import org.jivesoftware.whack.util.DBFilterMap;
import org.jivesoftware.whack.util.DBFilterMapList;
import org.jivesoftware.whack.util.DBSorterMap;
import org.jivesoftware.whack.util.DBSorterMapList;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

/**
 * 
 * @author v.dibi
 *
 */
public class DataBaseMongoImpl implements DataBase {
	
	Logger logger = LoggerFactory.getLogger(DataBaseMongoImpl.class);
	
	/**
	 * MongoDb configuration
	 */
	private MongoDbConfiguration dataBaseConfigMap;
	
	/**
	 * Json parser
	 */
	private CommonJSonParserImpl jSonParser;
	
	// MongoDb server
	private Mongo mongo;
	
	/**
	 * 
	 * @param dbName
	 * @return
	 * @throws DBException
	 */
	private DB getConnection(String dbName)  throws DBException  {
		
			DB db = null;
			logger.debug("Mongo url:" + dataBaseConfigMap.getMongoDbHost() + " - Mongo port: "+dataBaseConfigMap.geMongoDbPort());
			try {
				db = getMongo().getDB(dbName);
			} catch (MongoException mongoE) {
				logger.debug("Mongo connection trouble: " + mongoE.getMessage(), mongoE);
				throw new  DBException("Mongo connection trouble: " + mongoE.getMessage());
			}
			return db;	
	}
		
	@Override
	public void saveMessagePublishEntry(JSONObject jSONObject) throws DBException  {
		
			// Message can is save
			String isPersistence = jSONObject.get(MessagePublishEntry.ISPERSISTENCE).toString();
			if (Boolean.TRUE.toString().equals(isPersistence)) {
			
				// Collection name
				String messageCollection = (String) jSONObject.get(MessagePublishEntry.TYPE);
				if (messageCollection==null ||  "".equals(messageCollection)) {
					//collection default name
					messageCollection = dataBaseConfigMap.getMongoDbDefaultDbName();
				}
		
				// db name
				String messageDbName = (String) jSONObject.get(MessagePublishEntry.DBNAME);
				if (messageDbName==null || "".equals(messageDbName)) {
					//default db name
					messageDbName = dataBaseConfigMap.getMongoDbDefaultDbName();
				}
				DB db = getConnection(messageDbName);
				DBCollection dbCollection = db.getCollection(messageCollection);
				
				
				logger.debug("Save in db:" + messageDbName + " - Collection: " + messageCollection);
				
				//creation of an  document(as in a table in the relational database)
				//BasicDBObject document = new BasicDBObject();
				
							    
				//add data in the collection
				BasicDBObject document = new BasicDBObject();
				document.put(MessagePublishEntry.TYPE, (String) jSONObject.get(MessagePublishEntry.TYPE));
				document.put(MessagePublishEntry.AUTHOR, (String) jSONObject.get(MessagePublishEntry.AUTHOR));
//				document.put(MessagePublishEntry.PUBLISHEDDATE, (String) jSONObject.get(MessagePublishEntry.PUBLISHEDDATE));
				document.put(MessagePublishEntry.PUBLISHED, jSONObject.get(MessagePublishEntry.PUBLISHED));
				document.put(MessagePublishEntry.PUBLISHER, (String) jSONObject.get(MessagePublishEntry.PUBLISHER));
				document.put(MessagePublishEntry.HEADERS, jSONObject.get(MessagePublishEntry.HEADERS));
				document.put(MessagePublishEntry.LOCATION, jSONObject.get(MessagePublishEntry.LOCATION));
				document.put(MessagePublishEntry.CRITICITY, jSONObject.get(MessagePublishEntry.CRITICITY));
				document.put(MessagePublishEntry.RELEVANCE, jSONObject.get(MessagePublishEntry.RELEVANCE));
			
				// Try with simple payload
				try {
					JSONObject payload = (JSONObject) jSONObject.get(MessagePublishEntry.PAYLOAD);
					//document.put(MessagePublishEntry.PAYLOAD, payload.toString());					
					document.put(MessagePublishEntry.PAYLOAD, payload);
				} catch (ClassCastException ce) {
					
					// Try with complex payload
					try {
						List<JSONObject> payloadList = (List<JSONObject>) jSONObject.get(MessagePublishEntry.PAYLOAD);
						document.put(MessagePublishEntry.PAYLOAD, payloadList.toString());
					} catch (ClassCastException cce) {
						throw new DBException("Can't parse the message payload");
					}					
				}	
				try {
					dbCollection.insert(document);
				} catch (Exception e) {
					logger.error("Insert error: " + e.getMessage(), e);
					throw new DBException(e.getMessage()); 
				}
				 
				logger.info("Document created in: " + messageDbName + " - Collection: " + messageCollection);
				
				
			}
	}
	
	@Override
	public void saveComplexPublishEntry(JSONObject jSONObject) throws DBException  {
		List<JSONObject> messages = (List<JSONObject>) jSONObject.get(ComplexPublishEntry.MESSAGES); 
		 for (int i=0; i<messages.size(); i++) {
			 JSONObject message = messages.get(i);
			 saveMessagePublishEntry(message);
		 }
	}
	
	@Override
	public PayloadResultEntry findDocumentsByKeys(DataRequestEntry dataRequestEntry)  throws DBException {
		
		DB db = null;
		DBCollection dbCollection = null;
		PayloadResultEntry results = new PayloadResultEntry();
			
			// Find the db by name
			if (isDbExist(dataRequestEntry.getDbName())) {
				db = getConnection(dataRequestEntry.getDbName());
			} else {
				throw new DBException("Database " + dataRequestEntry.getDbName() + " doesn't exist in Mongo server !");
			}
					
			// Find the collection
			dbCollection = getCollection(dataRequestEntry.getCollectionName(), db);
			if (dbCollection==null) {
				throw new DBException("Collection " + dataRequestEntry.getCollectionName() + " doesn't exist in this database !");
			}
			
			logger.debug("Request db/collection: + " + dataRequestEntry.getDbName() + " / " + dataRequestEntry.getCollectionName() );
			
				
				// Récupération de la liste de paramètres
				List<ParamRequest> paramRequest = dataRequestEntry.getParamsRequest();
				// Si la liste est vide, le filtre = type et valeur = nom de la collection, afin de récupérer toutes les données de la base
				if(paramRequest.isEmpty()){
					// Build the mongo request
					String filter = MessagePublishEntry.TYPE;
					String value = dataRequestEntry.getCollectionName();
					BasicDBObject searchQuery = new BasicDBObject();
					searchQuery.put(filter, value);
					// Get results
					logger.debug("==> Mongo find start : " +  System.currentTimeMillis());
					DBCursor cursor = dbCollection.find(searchQuery);
					logger.debug("==> Mongo find end : " +  System.currentTimeMillis());
					while(cursor.hasNext()){
						DBObject dBObject = cursor.next();
						results.addResult(dBObjectToDataResultEntry(dBObject));
					}
				}
				else
				{	
					//List<String> id = new ArrayList<String>();
					for(int i=0; i<paramRequest.size(); i++){
						
						//logger.debug("params : " + paramRequest.get(i).toString());
						// Build the mongo request
						// Récupération de la liste de clé/valeur
						List<KeyRequest> params = paramRequest.get(i).getParams();
						DBFilterMapList keyList = new DBFilterMapList();
						// boucle sur un paramètre pouvant contenir différentes clés/valeurs
						for(int j=0; j<params.size(); j++){
							// Récupération des filtres et valeurs associées
							String filter = params.get(j).getFilter();
							String value = params.get(j).getValue().toString();
							String operator = params.get(j).getOperator().toString();
							
							// ajout des données dans un objet pour pouvoir les traiter plus tard
							keyList.addDBMapObject(new DBFilterMap(filter, new KeyRequest(filter, KeyRequest.getDBOperator(operator), value)));
							//logger.debug("KEYLIST : " + keyList.getDbMapList().get(j).toString());
						}
						
						List<SortRequest> sorts = paramRequest.get(i).getSorts();
						DBSorterMapList sortList = new DBSorterMapList();
						// boucle sur un paramètre pouvant contenir différentes clés/valeurs
						for(int j=0; j<sorts.size(); j++){
							// Récupération des filtres et valeurs associées
							String sorter = sorts.get(j).getSorter();
							String value = sorts.get(j).getValue().toString();
							
							// ajout des données dans un objet pour pouvoir les traiter plus tard
							sortList.addDBMapObject(new DBSorterMap(sorter, new SortRequest(sorter, value)));
						}
						
						// Get results
						logger.debug("==> Mongo find start : " +  System.currentTimeMillis());
						DBCursor cursor = dbCollection.find(toMongoRequest(keyList));
						logger.debug("==> Mongo find end : " +  System.currentTimeMillis());
						
						if(sortList.countDBMapObject() > 0){
							cursor = cursor.sort(toMongoRequest(sortList));
						}
						Limit limit = paramRequest.get(i).getLimit();
						if("true".equals(limit.isActive())){
							cursor = cursor.limit(new Integer(limit.getValue()));
						}
						
						int count = 0;
						if (paramRequest.get(i).isCount()) {
							count = cursor.count();
							// Add count to the message
							results.setCount(count);
						}
						
						while(cursor.hasNext()){
							DBObject dBObject = cursor.next();
							//logger.debug("DBOBJECT : " + dBObject.toString());
							results.addResult(dBObjectToDataResultEntry(dBObject));
						}
						logger.debug("Request return:" + results.getResults().size() + "results ");
					}
				}

		
		return results;
	}

	@Override
	public PayloadResultEntry groupDocumentsByKeys(DataRequestEntry dataRequestEntry)  throws DBException {
		
		DB db = null;
		DBCollection dbCollection = null;
		PayloadResultEntry results = new PayloadResultEntry();
			
			// Find the db by name
			if (isDbExist(dataRequestEntry.getDbName())) {
				db = getConnection(dataRequestEntry.getDbName());
			} else {
				throw new DBException("Database " + dataRequestEntry.getDbName() + " doesn't exist in Mongo server !");
			}
					
			// Find the collection
			dbCollection = getCollection(dataRequestEntry.getCollectionName(), db);
			if (dbCollection==null) {
				throw new DBException("Collection " + dataRequestEntry.getCollectionName() + " doesn't exist in this database !");
			}
			
			logger.debug("Request db/collection: + " + dataRequestEntry.getDbName() + " / " + dataRequestEntry.getCollectionName() );
				
				// Récupération de la liste de paramètres
				List<ParamRequest> paramRequest = dataRequestEntry.getParamsRequest();
				
				// Group Key
				BasicDBObject key = new BasicDBObject();
				for (int z=0; z<dataRequestEntry.getGroupKeys().size(); z++) {	
					String stKey = dataRequestEntry.getGroupKeys().get(z);
					key.put(stKey, 1);
				}
					
				// Group cond
				DBFilterMapList keyList = new DBFilterMapList();
				for(int i=0; i<paramRequest.size(); i++){
					List<KeyRequest> params = paramRequest.get(i).getParams();
					for(int j=0; j<params.size(); j++){
						String filter = params.get(j).getFilter();
						String value = params.get(j).getValue().toString();
						String operator = params.get(j).getOperator().toString();
						keyList.addDBMapObject(new DBFilterMap(filter, new KeyRequest(filter, KeyRequest.getDBOperator(operator), value)));
					}
				}
				BasicDBObject cond = toMongoRequest(keyList);
				
				// Group initial
				BasicDBObject initial = new BasicDBObject();
				initial.put(dataRequestEntry.getInitialKey() , dataRequestEntry.getInitialValue());
				
				// Group reduce
				String reduce = dataRequestEntry.getReduce();
				
				try {
					BasicDBList dbList = (BasicDBList)  dbCollection.group(key, cond, initial, reduce);
					
					if (dbList!=null) {
						for (int y=0; y<dbList.size(); y++) {
							CommandResult commandResult = (CommandResult) dbList.get(y);
							DataResultEntry dataResultEntry = new DataResultEntry();
							dataResultEntry.setAuthor("hserver");
							dataResultEntry.setPublishedDate(new Date());
							dataResultEntry.setPublisher("hserver");
							dataResultEntry.setType("group request");
							dataResultEntry.setPayload(commandResult.toString());
							results.addResult(dataResultEntry);
						}
					}
					
					logger.debug(dbList.toString());
					
				} catch (MongoException e) {
					throw new DBException(e.getMessage(), e);
				}
	
				return results;
	}
	
	@Override
	public PayloadResultEntry distinctDocumentsByKeys(DataRequestEntry dataRequestEntry)  throws DBException {
		
		DB db = null;
		DBCollection dbCollection = null;
		PayloadResultEntry results = new PayloadResultEntry();
			
			// Find the db by name
			if (isDbExist(dataRequestEntry.getDbName())) {
				db = getConnection(dataRequestEntry.getDbName());
			} else {
				throw new DBException("Database " + dataRequestEntry.getDbName() + " doesn't exist in Mongo server !");
			}
					
			// Find the collection
			dbCollection = getCollection(dataRequestEntry.getCollectionName(), db);
			if (dbCollection==null) {
				throw new DBException("Collection " + dataRequestEntry.getCollectionName() + " doesn't exist in this database !");
			}
			
			logger.debug("Request db/collection: + " + dataRequestEntry.getDbName() + " / " + dataRequestEntry.getCollectionName() );
				
				// Récupération de la liste de paramètres
				List<ParamRequest> paramRequest = dataRequestEntry.getParamsRequest();
				
				// Key
				String key = "";
				for (int z=0; z<dataRequestEntry.getGroupKeys().size(); z++) {	
					key = dataRequestEntry.getGroupKeys().get(z);
				}
					
				// cond
				BasicDBObject query = new BasicDBObject();
				DBFilterMapList keyList = new DBFilterMapList();
				for(int i=0; i<paramRequest.size(); i++){
					List<KeyRequest> params = paramRequest.get(i).getParams();
					for(int j=0; j<params.size(); j++){
						String filter = params.get(j).getFilter();
						String value = params.get(j).getValue().toString();
						String operator = params.get(j).getOperator().toString();
						keyList.addDBMapObject(new DBFilterMap(filter, new KeyRequest(filter, KeyRequest.getDBOperator(operator), value)));
					}
				}
				query = toMongoRequest(keyList);
				
				try {
					BasicDBList dbList = (BasicDBList) dbCollection.distinct(key, query);
					
					/*
					db.twittersocialtv.distinct("author",
					{"published":{"$gte":"2011/12/12 00:00:00"}, "published":{"$lte":"2011/12/13 00:00:00"}, "headers_chprog":"f4#frends"} )
					*/
					
					if (dbList!=null) {
						DataResultEntry dataResultEntry = new DataResultEntry();
						dataResultEntry.setAuthor("hserver");
						dataResultEntry.setPublishedDate(new Date());
						dataResultEntry.setPublisher("hserver");
						dataResultEntry.setType("distinct request");
						dataResultEntry.setPayload(String.valueOf(dbList.size()));
						results.addResult(dataResultEntry);
					}
					
					logger.debug(dbList.toString());
					
				} catch (MongoException e) {
					throw new DBException(e.getMessage(), e);
				}
	
				return results;

	}
	
		
	/**
	 * Find is data base name exist in Mongo server
	 * @param dbName the db name to find
	 * @return if data base name exist in Mongo server
	 */
	public boolean isDbExist(String dbName) throws DBException {
		boolean result = false;

		List<String> dbList = getMongo().getDatabaseNames();
		if (dbList!=null) {
			if (dbList.contains(dbName)) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 *  fin the collection if exist
	 * @param collectionName
	 * @return
	 */
	public DBCollection getCollection(String collectionName, DB db){
		DBCollection resutl = null;
		if (db.collectionExists(collectionName)) {
			resutl = db.getCollection(collectionName);
		} else {
			logger.error("Collection " + collectionName + " not exist for dbName " + db.getName());
		}
		return resutl;		
	}
	
	/**
	 * Get MongoDb 
	 * @return MongoDb
	 */
	private Mongo getMongo() throws DBException {
		if (mongo==null) {
			try {
				 MongoOptions  mongoOptions = new  MongoOptions();
				 mongoOptions.connectionsPerHost = dataBaseConfigMap.getMongoDbPoolSize();
				 ServerAddress serverAddress = new ServerAddress(dataBaseConfigMap.getMongoDbHost(), dataBaseConfigMap.geMongoDbPort());
				 mongo = new Mongo(serverAddress, mongoOptions);
			}  catch (UnknownHostException unknownHostE) {
				throw new DBException("Unknow Host " + dataBaseConfigMap.getMongoDbHost() + unknownHostE.getMessage());
			} catch (MongoException mongoE) {
				throw new DBException("Mongo connection trouble: " + mongoE.getMessage());
			}
		}
		return mongo;
	}

	/**
	 * 
	 * @param mongo
	 */
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}
	
	/**
	 * Change DBOject into DataResultEntry
	 * @param dBObject
	 * @return DataResultEntry
	 */
		public DataResultEntry dBObjectToDataResultEntry(DBObject dBObject){
		
		String type = (String) dBObject.get(MessagePublishEntry.TYPE);
		String author = (String) dBObject.get(MessagePublishEntry.AUTHOR);
//		String publishDateSt = (String) dBObject.get(MessagePublishEntry.PUBLISHEDDATE);
//		Date publishedDate = MessagePublishEntry.getFormatedPublishedDate(publishDateSt);
		Long publishDateTime = (Long) dBObject.get(MessagePublishEntry.PUBLISHED);
		if (publishDateTime == null) {
			logger.error("Can't parse the date: " + publishDateTime);
			publishDateTime = Long.MIN_VALUE;
		}
		Date publishedDate = new Date(publishDateTime.longValue());
		 
		String publisher = (String) dBObject.get(MessagePublishEntry.PUBLISHER);
//		String payload = (String) dBObject.get(MessagePublishEntry.PAYLOAD);
//		if (payload != null) {
//			payload = CommonJSonParserImpl.encode(payload);
//		}
		Object payload = dBObject.get(MessagePublishEntry.PAYLOAD);
		Object headers = dBObject.get(MessagePublishEntry.HEADERS);
		Object location = dBObject.get(MessagePublishEntry.LOCATION);
		
		DataResultEntry dataResultEntry = new DataResultEntry();
		dataResultEntry.setType(type);
		dataResultEntry.setAuthor(author);
		dataResultEntry.setPublishedDate(publishedDate);
		dataResultEntry.setPublisher(publisher);
		if (payload != null) {
			dataResultEntry.setPayload(payload.toString());
		}
		
		if (headers != null){
			dataResultEntry.setHeader(headers.toString());
		}
		
		if (location != null){
			dataResultEntry.setLocation(location.toString());
		}
		
		//logger.debug("\nauthor : " + author + "\npublishDate : " + publishedDate + "\npublisher : " + publisher);
		return dataResultEntry;
	}
	
	public BasicDBObject toMongoRequest(DBFilterMapList dbFilterMapList){
		BasicDBObject searchQuery = new BasicDBObject();
		// si dbmaplist n'est pas vide
		if(dbFilterMapList.countDBMapObject()>0){
			while(dbFilterMapList.countDBMapObject() > 0){
				// on récupère le filtre du premier objet
				String filter = dbFilterMapList.getDbMapList().get(0).getFilter();
				// Récupération des objets avec le même filtre
				
				List<DBFilterMap> sameFilter = dbFilterMapList.GetDBMapObjects(filter);
	 
				if(sameFilter.size() == 1){
					//searchQuery.put(sameFilter.get(0).getKey().getFilter(), sameFilter.get(0).getKey().getValue());
					sameFilter.get(0).getKey().getOperator().appendQuery(searchQuery, sameFilter.get(0).getKey().getFilter(), sameFilter.get(0).getKey().getValue());
				}
				// On suppose que les opérateurs seront toujours corrects car la requete ne serait pas correctement exécutée
				else if(sameFilter.size() == 2){
					DBFilterMap kr1 = sameFilter.get(0);
					DBFilterMap kr2 = sameFilter.get(1);
					searchQuery.put(filter, new BasicDBObject(KeyRequest.getMongoOperator(kr1.getKey().getOperator()), kr1.getKey().getValue().toString())
						.append(KeyRequest.getMongoOperator(kr2.getKey().getOperator()), kr2.getKey().getValue().toString()));

				}
				// a la fin du traitement, suppression des objets avec le filtre utilisé
				dbFilterMapList.deleteDBMapObject(filter);
			}
			logger.debug("Search query: " + searchQuery.toString());
			return searchQuery;
		}
		else return null;
	}
	
	public BasicDBObject toMongoRequest(DBSorterMapList dbSorterMapList){
		// si dbmaplist n'est pas vide
		BasicDBObject searchQuery = new BasicDBObject();
		if(dbSorterMapList.countDBMapObject()>0){
			while(dbSorterMapList.countDBMapObject() > 0){
				// on récupère le filtre du premier objet
				String sorter = dbSorterMapList.getDbMapList().get(0).getSorter();
				// Récupération des objets avec le même filtre
				
				List<DBSorterMap> sameSorter = dbSorterMapList.GetDBMapObjects(sorter);
	 
				if(sameSorter.size() == 1){
					searchQuery.put(sameSorter.get(0).getSort().getSorter(), SortRequest.getMongoSort(SortRequest.getDBSort(sameSorter.get(0).getSort().getValue())));
				}
				// a la fin du traitement, suppression des objets avec le filtre utilisé
				dbSorterMapList.deleteDBMapObject(sorter);
			}
			logger.debug("Search query: " + searchQuery.toString());
			return searchQuery;
		}
		else return null;
	}
	
	public List<String> getDatabaseNames(){
		List<String> databaseNames = null;
		try {
			databaseNames =  getMongo().getDatabaseNames();
		} catch (MongoException e) {
			logger.error("Mongo Object doesn't exist.");
		} catch (DBException e) {
			logger.error("Database doesn't exist.");
		}
		return databaseNames;
	}
	
	 // Spring injections ------------------------------------
    @Required
    public void setDataBaseConfigMap(MongoDbConfiguration dataBaseConfigMap) {
		this.dataBaseConfigMap = dataBaseConfigMap;
	}	
    
    
    @Required
    public void setJSonParser(CommonJSonParserImpl jSonParser) {
		this.jSonParser = jSonParser;
	}

	@Override
	public void updateDocumentByKeys(JSONObject updateDataQuery) throws DBException {
		JSONObject params = (JSONObject) updateDataQuery.get("paramsRequest");
		DB db = null;
		DBCollection dbCollection = null;
		// Find the db by name
		db = getConnection(updateDataQuery.get("dbName").toString());
		// Find the collection
		dbCollection = getCollection(updateDataQuery.get("collectionName").toString(), db);
		
		DBObject filters = new BasicDBObject(params);
		BasicDBObject dataToBeFiltered = new BasicDBObject((JSONObject) updateDataQuery.get("data"));
		DBObject dataToBeUpdated = new BasicDBObject();
		dataToBeUpdated.put(MessagePublishEntry.TYPE, dataToBeFiltered.get(MessagePublishEntry.TYPE));
		dataToBeUpdated.put(MessagePublishEntry.AUTHOR, dataToBeFiltered.get(MessagePublishEntry.AUTHOR));
		dataToBeUpdated.put(MessagePublishEntry.PUBLISHER, dataToBeFiltered.get(MessagePublishEntry.PUBLISHER));
		dataToBeUpdated.put(MessagePublishEntry.PUBLISHED, dataToBeFiltered.get(MessagePublishEntry.PUBLISHED));
		dataToBeUpdated.put(MessagePublishEntry.HEADERS, dataToBeFiltered.get(MessagePublishEntry.HEADERS));
		dataToBeUpdated.put(MessagePublishEntry.LOCATION, dataToBeFiltered.get(MessagePublishEntry.LOCATION));
		dataToBeUpdated.put(MessagePublishEntry.PAYLOAD, dataToBeFiltered.get(MessagePublishEntry.PAYLOAD));
		dbCollection.update(filters, dataToBeUpdated);		
		
	}
}