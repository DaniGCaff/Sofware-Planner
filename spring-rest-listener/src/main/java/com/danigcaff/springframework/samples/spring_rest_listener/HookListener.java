package com.danigcaff.springframework.samples.spring_rest_listener;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.util.MongoManager;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@RestController
public class HookListener {
	private static String TYPE = "event_type";
	private static String PUSH_TYPE = "pusher";
	private static String MEMBER_TYPE = "member";

	public JSONObject eventClasification(JSONObject json) {
		if (json.has(PUSH_TYPE))
			json.put(TYPE, PUSH_TYPE);
		else if (json.has(MEMBER_TYPE))
			json.put(TYPE, MEMBER_TYPE);
		return json;
	}

	@RequestMapping(value = "/listen/github", method = RequestMethod.POST)
	public void gitHubListener(@RequestBody String evento) {
		//TODO Comprobar que la url del repositorio existe en la colección de autorizados.
		System.out.println(evento);
		JSONObject json = new JSONObject(evento);
		json = eventClasification(json);
		DB database = MongoManager.getManager().getDatabase();
		DBObject doc = (DBObject) (JSON.parse(json.toString()));
		if (!database.collectionExists((String) json.get(TYPE))) {
			DBObject options = BasicDBObjectBuilder.start().add("capped", false).get();
			database.createCollection((String) json.get(TYPE), options);
		}
		DBCollection coll = database.getCollection((String) json.get(TYPE));
		coll.insert(doc);
		
		if (json.has(PUSH_TYPE)){
			ArrayList<Map <String,String>> mapCommitTask = getDataFromCommit(json);
			Iterator<Map<String, String>> iterador = mapCommitTask.iterator();
			while(iterador.hasNext()) {
				Map<String, String> asocCommitTask = iterador.next();
				BasicDBObject query = new BasicDBObject("shortUrl",asocCommitTask.get("urlTask"));
				DBCollection collTask = database.getCollection("taskPrueba");
				DBObject docResult = collTask.findOne(query);
				if(docResult != null) {
					String idTarjeta = docResult.get("id").toString();
					if (!database.collectionExists("ASOCIADOS")) {
						DBObject options = BasicDBObjectBuilder.start().add("capped", false).get();
						database.createCollection("ASOCIADOS", options);
					}
					DBCollection collAsociado = database.getCollection("ASOCIADOS");
					BasicDBObject docAsociado = new BasicDBObject("idTarjeta",idTarjeta)
												.append("idCommit", asocCommitTask.get("idCommit"));
					collAsociado.insert(docAsociado);
				} else
					System.out.println("No hay resultados...");
			}
			
		}
	}
	
	public ArrayList<Map<String, String>> getDataFromCommit(JSONObject json){
		ArrayList<Map <String,String>> mapUrlIds = new ArrayList<Map <String,String>>();
		String url;
		String id;
		String message;
		JSONArray commitsArray = (JSONArray)json.get("commits");
		for (int i = 0; i < commitsArray.length(); i++) {
		    JSONObject rec = commitsArray.getJSONObject(i);
		    id = rec.getString("id");
		    message = rec.getString("message");
		    
		    	Pattern pattern = Pattern.compile("@https://trello.com/c/\\w+@");
		    	Matcher matcher = pattern.matcher(message);
		    	while (matcher.find()){
		    		Map <String, String> map = new HashMap <String, String>();
		    	       url = matcher.group().substring(1, matcher.group().length()-1);
		    	       map.put("idCommit", id);
		    	       map.put("urlTask", url);
		    	       mapUrlIds.add(map);
		    	}
		}
  	  return mapUrlIds;		
	}
}
