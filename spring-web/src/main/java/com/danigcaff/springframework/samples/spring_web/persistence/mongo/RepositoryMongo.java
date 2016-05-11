package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.persistence.Repository;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager.COLLECTIONS;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RepositoryMongo extends EntityAbstractMongo implements Repository {
	
	private String repoName;
	private String repoId;
	private String owner;
	private String boardId;
	private Boolean asoc;
	public enum FIELDS {id, repoName, creation, lastModification, owner, repoId, boardId, asoc}
	public RepositoryMongo(String repoId) {
		super(repoId);
		this.repoId=repoId;
	}
	public String getRepoName() {
		return repoName;
	}

	public Repository setRepoName(String repoName) {
		this.repoName = repoName;
		return this;
	}
	protected static DBObject allFields = BasicDBObjectBuilder.start()
			.add(FIELDS.id.name(), 1).add(FIELDS.repoName.name(), 1).add(FIELDS.creation.name(), 1).add(FIELDS.lastModification.name(), 1)
			.add(FIELDS.owner.name(), 1).add(FIELDS.repoId.name(), 1)
			.add(FIELDS.boardId.name(), 1).add(FIELDS.asoc.name(), 1)
			.get();
	public String getRepoId() {
		return repoId;
	}

	public Repository setRepoId(String repoId) {
		this.repoId = repoId;
		return this;
	}

	public Boolean getAsoc() {
		if(asoc != null)
			return asoc;
		return false;
	}

	public Repository setAsoc(Boolean asoc) {
		this.asoc = asoc;
		return this;
	}
	
	public String getOwner() {
		return owner;
	}

	public Repository setOwner(String owner) {
		this.owner = owner;
		return this;
	}

	public String getBoardId() {
		return this.boardId;
	}

	public Repository setBoardId(String boardId) {
		this.boardId = boardId;
		return this;
	}
	
	@Override
	public void save() {
		DBObject query = new BasicDBObject(FIELDS.repoId.name(), repoId);
		DBObject doc = BasicDBObjectBuilder.start()
						.add(FIELDS.repoName.name(), repoName)
						.add(FIELDS.owner.name(), owner)
						.add(FIELDS.repoId.name(), repoId)
						.add(FIELDS.boardId.name(), boardId)
						.add(FIELDS.creation.name(), creation)
						.get();
		DBCollection coll = MongoManager.getManager().getCollection(COLLECTIONS.AUTORIZADOS);
		coll.update(query, doc);
	}


	@Override
	public Entity readById() {
		DBObject filter = BasicDBObjectBuilder.start().add(FIELDS.repoId.name(), id).get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.AUTORIZADOS);
		DBObject result = coll.findOne(filter, allFields);
		if(result != null) {
			this.repoName = (String) result.get(FIELDS.repoName.name());
			this.owner = (String) result.get(FIELDS.owner.name());
			this.repoId = (String) result.get(FIELDS.repoId.name());
			this.boardId = (String) result.get(FIELDS.boardId.name());
			this.asoc = (Boolean) result.get(FIELDS.asoc.name());
			this.lastModification = (String) result.get(FIELDS.lastModification.name());
			this.creation = (String) result.get(FIELDS.creation.name());
			return this;
		}
		return null;
	}
	
	public static RepositoryMongo parse(DBObject object) {
		RepositoryMongo repo = new RepositoryMongo((String)object.get(FIELDS.id.name()));
		repo.setRepoName((String)object.get(FIELDS.repoName.name()));
		repo.setOwner((String)object.get(FIELDS.owner.name()));
		repo.setRepoId((String)object.get(FIELDS.repoId.name()));
		repo.setBoardId((String)object.get(FIELDS.boardId.name()));
		repo.setAsoc((Boolean)object.get(FIELDS.asoc.name()));
		repo.setLastModificationDate((String)object.get(FIELDS.creation.name()));
		repo.setCreationDate((String)object.get(FIELDS.creation.name()));
		return repo;
	}
	
	public static void insert(Map<String, String> data) {
		DBObject doc = BasicDBObjectBuilder.start()
				.add(FIELDS.repoName.name(), data.get(FIELDS.repoName.name()))
				.add(FIELDS.owner.name(), data.get(FIELDS.owner.name()))
				.add(FIELDS.repoId.name(), data.get(FIELDS.repoId.name()))
				.add(FIELDS.boardId.name(), data.get(FIELDS.boardId.name()))
				.add(FIELDS.creation.name(), data.get(FIELDS.creation.name()))
				.add(FIELDS.asoc.name(), false)

				.get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.AUTORIZADOS);
		coll.insert(doc);
	}
	
	public static List <Repository> listAll(String user){
		List <Repository> reposList = new ArrayList<Repository>();
		DBObject query = new BasicDBObject("owner",user);
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.AUTORIZADOS);
		DBCursor cursor = coll.find(query);
		if (cursor.hasNext()){
			DBObject doc =cursor.next();
			Repository  repository = RepositoryMongo.parse(doc);
			reposList.add(repository);
		}
		return reposList;
	}

}
