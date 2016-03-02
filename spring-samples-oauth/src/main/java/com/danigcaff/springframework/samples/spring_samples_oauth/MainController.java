package com.danigcaff.springframework.samples.spring_samples_oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

@Controller
public class MainController
{
    private MongoCollection coll;
    private MongoDatabase database;
    private MongoClient client;
    @RequestMapping("/")
    @ResponseBody
    public String index() {
    	return "Hola mundo!!";
    }
    
    public void mongoDBConnection(){
    	client = new MongoClient("localhost", 27017);
    	database=client.getDatabase("trellogithub");
    	coll=database.getCollection("eventos");
    }
    
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ResponseBody
    public String eventListener(@RequestBody String evento) {
    	System.out.println(evento);
    	mongoDBConnection();
    	 DBObject doc = (DBObject) JSON.parse(evento);
    	 
         coll.insertOne(doc);
    	return "OK";
    }
    
}
