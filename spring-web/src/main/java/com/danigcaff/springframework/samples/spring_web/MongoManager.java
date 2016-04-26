package com.danigcaff.springframework.samples.spring_web;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoManager {
	private static MongoManager instance = null;
	private Mongo client;
	private DB database;
	
	public enum COLLECTIONS { AUTORIZADOS }
	
	private MongoManager() {
		try {
			client = new MongoClient("localhost", 27017);
			database = client.getDB("trellogithub");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static MongoManager getManager() {
		if(instance == null) {
			instance = new MongoManager();
		}
		return instance;
	}

	public Mongo getClient() {
		return client;
	}

	public DB getDatabase() {
		return database;
	}
	
	
}
