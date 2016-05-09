package com.danigcaff.springframework.samples.spring_web.loader.implementation;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.danigcaff.springframework.samples.spring_web.util.MongoManager;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
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
		if (!database.collectionExists(TYPE)) {
			DBObject options = BasicDBObjectBuilder.start().add("capped", false).get();
			database.createCollection((String) json.get(TYPE), options);
		}
		DBCollection coll = database.getCollection((String) json.get(TYPE));
		//TODO Parsear el comentario del commit. 
		//TODO Hay que tratar commit por commit, y añadir la tarea a un array dentro del objeto repositorio
		// en la coleccion autorizados.
		
		coll.insert(doc);
	}

}
