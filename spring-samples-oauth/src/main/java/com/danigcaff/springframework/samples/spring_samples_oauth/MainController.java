package com.danigcaff.springframework.samples.spring_samples_oauth;
import org.json.JSONObject;	
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

@Controller
public class MainController
{
    private DBCollection coll;
    private DB database;
    private Mongo client;
    private static String TYPE = "event_type";
    @RequestMapping("/")
    @ResponseBody
    public String index() {
    	return "Hola mundo!!";
    }
    
    @SuppressWarnings("deprecation")
	public void mongoDBConnection(){
    	client = new MongoClient("localhost", 27017);
    	database=client.getDB("trellogithub");
    }
    
    public JSONObject eventClasification(JSONObject json){
    	if (json.has("pusher"))
    		json.put(TYPE, "push");
    	else if (json.has("member"))
    		json.put(TYPE, "member");
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
    	if (!database.collectionExists(TYPE)){
    		DBObject options = BasicDBObjectBuilder.start().add("capped", false).get();
    		database.createCollection((String)json.get(TYPE),options);        
    		}
    	coll=database.getCollection((String)json.get(TYPE)); 
        coll.insert(doc);
    	return "OK";
    }
    
    
    
}
