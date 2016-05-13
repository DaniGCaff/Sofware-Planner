package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.danigcaff.springframework.samples.spring_web.persistence.Board;
import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager.COLLECTIONS;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BoardMongo extends EntityAbstractMongo implements Board{
	
	private String owner;
	private String boardName;
	
	public enum FIELDS {id, owner, boardName, creation, lastModification}
	public BoardMongo(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getBoardName() {
		return boardName;
	}

	public Board setBoardName(String boardName) {
		this.boardName = boardName;
		return this;
	}

	public String getOwner() {
		return owner;
	}

	public Board setOwner(String owner) {
		this.owner = owner;
		return this;
	}

	@Override
	public void save() {
		DBObject query = new BasicDBObject(FIELDS.id.name(), id);
		DBObject doc = BasicDBObjectBuilder.start()
						.add(FIELDS.boardName.name(), boardName)
						.add(FIELDS.owner.name(), owner)
						.add(FIELDS.creation.name(), creation)
						.add(FIELDS.lastModification.name(), lastModification)
						.get();
		DBCollection coll = MongoManager.getManager().getCollection(COLLECTIONS.BOARDS);
		coll.update(query, doc);
	}


	@Override
	public Entity readById() {
		DBObject filter = BasicDBObjectBuilder.start().add(FIELDS.id.name(), id).get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.BOARDS);
		DBObject result = coll.findOne(filter);
		if(result != null) {
			this.boardName = (String) result.get(FIELDS.boardName.name());
			this.owner = (String) result.get(FIELDS.owner.name());
			this.id = (String) result.get(FIELDS.id.name());
			this.lastModification = (String) result.get(FIELDS.lastModification.name());
			this.creation = (String) result.get(FIELDS.creation.name());
			return this;
		}
		return null;
	}
	
	public static BoardMongo parse(DBObject object) {
		BoardMongo board = new BoardMongo((String)object.get(FIELDS.id.name()));
		board.setBoardName((String)object.get(FIELDS.boardName.name()));
		board.setOwner((String)object.get(FIELDS.owner.name()));
		board.setLastModificationDate((String)object.get(FIELDS.creation.name()));
		board.setCreationDate((String)object.get(FIELDS.creation.name()));
		return board;
	}
	
	public static void insert(Map<String, String> data) {
		DBObject doc = BasicDBObjectBuilder.start()
				.add(FIELDS.boardName.name(), data.get(FIELDS.boardName.name()))
				.add(FIELDS.owner.name(), data.get(FIELDS.owner.name()))
				.add(FIELDS.id.name(), data.get(FIELDS.id.name()))
				.add(FIELDS.creation.name(), data.get(FIELDS.creation.name()))
				.add(FIELDS.lastModification.name(), data.get(FIELDS.lastModification.name()))

				.get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.BOARDS);
		coll.insert(doc);
	}
	
	public static List <Board> listAll(String owner){
		List <Board> boardsList = new ArrayList<Board>();
		DBObject query = new BasicDBObject("owner",owner);
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.BOARDS);
		DBCursor cursor = coll.find(query);
		while (cursor.hasNext()){
			DBObject doc =cursor.next();
			Board  board = BoardMongo.parse(doc);
			boardsList.add(board);
		}
		return boardsList;
	}


}
