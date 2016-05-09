package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import java.util.Map;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.persistence.User;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class UserMongo extends EntityAbstractMongo implements User {

	protected String name;
	protected String gitHubUserId;
	protected String trelloUserId;
	protected String password;
	enum FIELDS {id, creation, lastModification, name, password, gitHubUserId, trelloUserId}
	protected static DBObject allFields = BasicDBObjectBuilder.start()
			.add(FIELDS.id.name(), 1).add(FIELDS.creation.name(), 1).add(FIELDS.creation.name(), 1)
			.add(FIELDS.name.name(), 1).add(FIELDS.gitHubUserId.name(), 1)
			.add(FIELDS.trelloUserId.name(), 1).add(FIELDS.password.name(), 1)
			.get();
	
	public UserMongo(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}
	
	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getGitHubUserId() {
		return gitHubUserId;
	}

	public User setGitHubUserId(String gitHubUserId) {
		this.gitHubUserId = gitHubUserId;
		return this;
	}

	public String getTrelloUserId() {
		return trelloUserId;
	}

	public User setTrelloUser(String trelloUserId) {
		this.trelloUserId = trelloUserId;
		return this;
	}

	@Override
	public void save() {
		DBObject doc = BasicDBObjectBuilder.start()
						.add(FIELDS.name.name(), name)
						.add(FIELDS.gitHubUserId.name(), gitHubUserId)
						.add(FIELDS.trelloUserId.name(), trelloUserId)
						.add(FIELDS.password.name(), password)
						.get();
		// TODO ... upsert
	}

	@Override
	public Entity readById() {
		DBObject filter = BasicDBObjectBuilder.start().add(FIELDS.id.name(), id).get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.USERS);
		DBObject result = coll.findOne(filter, allFields);
		return UserMongo.parse(result);
	}
	
	public static User parse(DBObject object) {
		User user = new UserMongo((String)object.get(FIELDS.id.name()));
		user.setName((String)object.get(FIELDS.name.name()));
		user.setPassword((String)object.get(FIELDS.password.name()));
		user.setGitHubUserId((String)object.get(FIELDS.gitHubUserId.name()));
		user.setTrelloUser((String)object.get(FIELDS.trelloUserId.name()));
		user.setLastModificationDate((String)object.get(FIELDS.creation.name()));
		user.setCreationDate((String)object.get(FIELDS.creation.name()));
		return user;
	}
	
	public static void insert(Map<String, String> data) {
		DBObject doc = BasicDBObjectBuilder.start()
				.add(FIELDS.name.name(), data.get(FIELDS.name.name()))
				.add(FIELDS.gitHubUserId.name(), data.get(FIELDS.gitHubUserId.name()))
				.add(FIELDS.trelloUserId.name(), data.get(FIELDS.trelloUserId.name()))
				.add(FIELDS.password.name(), data.get(FIELDS.password.name()))
				.get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.USERS);
		coll.insert(doc);
	}

}
