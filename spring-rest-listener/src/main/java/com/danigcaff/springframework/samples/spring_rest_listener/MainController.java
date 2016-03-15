package com.danigcaff.springframework.samples.spring_rest_listener;

import java.net.UnknownHostException;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@Controller
public class MainController {
	private DBCollection coll;
	private DB database;
	private Mongo client;
	private static String TYPE = "event_type";
	private static String PUSH_TYPE = "pushER";
	private static String MEMBER_TYPE = "member";

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "Hola mundo!!";
	}

	public void mongoDBConnection() {
		try {
			client = new MongoClient("localhost", 27017);
			database = client.getDB("trellogithub");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject eventClasification(JSONObject json) {
		if (json.has(PUSH_TYPE))
			json.put(TYPE, PUSH_TYPE);
		else if (json.has(MEMBER_TYPE))
			json.put(TYPE, MEMBER_TYPE);
		return json;
	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	@ResponseBody
	public String eventListener(@RequestBody String evento) {
		System.out.println(evento);
		mongoDBConnection();
		JSONObject json = new JSONObject(evento);
		json = eventClasification(json);

		DBObject doc = (DBObject) (JSON.parse(json.toString()));
		if (!database.collectionExists(TYPE)) {
			DBObject options = BasicDBObjectBuilder.start().add("capped", false).get();
			database.createCollection((String) json.get(TYPE), options);
		}
		coll = database.getCollection((String) json.get(TYPE));
		coll.insert(doc);
		return "OK";
	}

}
