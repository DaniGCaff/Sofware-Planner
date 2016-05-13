package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.persistence.Task;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager.COLLECTIONS;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class TaskMongo extends EntityAbstractMongo implements Task {
	protected String dateLastActivity;
	protected String idBoard;
	protected String idList;
	protected String name;
	protected String pos;
	protected String due;
	protected String shortUrl;

	public enum FIELDS {id, creation, lastModification, dateLastActivity, idBoard, idList, name, pos, due, shortUrl}

	protected static DBObject allFields = BasicDBObjectBuilder.start()
			.add(FIELDS.id.name(), 1).add(FIELDS.creation.name(), 1).add(FIELDS.lastModification.name(), 1)
			.add(FIELDS.dateLastActivity.name(), 1).add(FIELDS.idBoard.name(), 1)
			.add(FIELDS.idList.name(), 1).add(FIELDS.name.name(), 1).add(FIELDS.pos.name(), 1)
			.add(FIELDS.due.name(), 1).add(FIELDS.shortUrl.name(), 1)
			.get();
	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#getDateLastActivity()
	 */
	public String getDateLastActivity() {
		return dateLastActivity;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#setDateLastActivity(java.lang.String)
	 */
	public Task setDateLastActivity(String dateLastActivity) {
		this.dateLastActivity = dateLastActivity;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#getIdBoard()
	 */
	public String getIdBoard() {
		return idBoard;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#setIdBoard(java.lang.String)
	 */
	public Task setIdBoard(String idBoard) {
		this.idBoard = idBoard;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#getIdList()
	 */
	public String getIdList() {
		return idList;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#setIdList(java.lang.String)
	 */
	public Task setIdList(String idList) {
		this.idList = idList;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#setName(java.lang.String)
	 */
	public Task setName(String name) {
		this.name = name;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#getPos()
	 */
	public String getPos() {
		return pos;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#setPos(java.lang.String)
	 */
	public Task setPos(String pos) {
		this.pos = pos;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#getDue()
	 */
	public String getDue() {
		return due;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#setDue(java.lang.String)
	 */
	public Task setDue(String due) {
		this.due = due;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#getShortUrl()
	 */
	public String getShortUrl() {
		return shortUrl;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.persistence.mongo.Task#setShortUrl(java.lang.String)
	 */
	public Task setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
		return this;
	}

	// TODO labels... JSONArray...JSONObject...	
	public TaskMongo(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void save() {
		DBObject query = new BasicDBObject(FIELDS.id.name(), id);
		DBObject doc = BasicDBObjectBuilder.start()
						.add(FIELDS.creation.name(), creation)
						.add(FIELDS.lastModification.name(), lastModification)
						.add(FIELDS.dateLastActivity.name(), dateLastActivity)
						.add(FIELDS.idBoard.name(), idBoard)
						.add(FIELDS.idList.name(), idList)
						.add(FIELDS.name.name(), name)
						.add(FIELDS.pos.name(), pos)
						.add(FIELDS.due.name(), due)
						.add(FIELDS.shortUrl.name(), shortUrl)
						.get();
		DBCollection coll = MongoManager.getManager().getCollection(COLLECTIONS.TASKS);
		coll.update(query, doc);
	}

	@Override
	public Entity readById() {
		DBObject filter = BasicDBObjectBuilder.start().add(FIELDS.id.name(), id).get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.TASKS);
		DBObject result = coll.findOne(filter, allFields);
		if(result != null) {
			this.creation = (String) result.get(FIELDS.creation.name());
			this.lastModification = (String) result.get(FIELDS.lastModification.name());
			this.dateLastActivity = (String) result.get(FIELDS.dateLastActivity.name());
			this.idBoard = (String) result.get(FIELDS.idBoard.name());
			this.idList = (String) result.get(FIELDS.idList.name());
			this.name = (String) result.get(FIELDS.name.name());
			this.pos = (String) result.get(FIELDS.pos.name());
			this.due = (String) result.get(FIELDS.due.name());
			this.shortUrl = (String) result.get(FIELDS.shortUrl.name());

			return this;
		}
		return null;
	}
	
	public static TaskMongo parse(DBObject object) {
		TaskMongo task = new TaskMongo((String)object.get(FIELDS.id.name()));
		task.setCreationDate((String)object.get(FIELDS.creation.name()));
		task.setLastModificationDate((String)object.get(FIELDS.lastModification.name()));
		task.setDateLastActivity((String)object.get(FIELDS.dateLastActivity.name()));
		task.setIdBoard((String)object.get(FIELDS.idBoard.name()));
		task.setIdList((String)object.get(FIELDS.idList.name()));
		task.setName((String)object.get(FIELDS.name.name()));
		task.setPos((String)object.get(FIELDS.pos.name()));
		task.setDue((String)object.get(FIELDS.due.name()));
		task.setShortUrl((String)object.get(FIELDS.shortUrl.name()));

		return task;
	}
	
	public static void insert(Map<String, String> data) {
		DBObject doc = BasicDBObjectBuilder.start()
				.add(FIELDS.id.name(), data.get(FIELDS.id.name()))
				.add(FIELDS.creation.name(), data.get(FIELDS.creation.name()))
				.add(FIELDS.lastModification.name(), data.get(FIELDS.lastModification.name()))
				.add(FIELDS.dateLastActivity.name(), data.get(FIELDS.dateLastActivity.name()))
				.add(FIELDS.idBoard.name(), data.get(FIELDS.idBoard.name()))
				.add(FIELDS.idList.name(), data.get(FIELDS.idList.name()))
				.add(FIELDS.name.name(), data.get(FIELDS.name.name()))
				.add(FIELDS.pos.name(), data.get(FIELDS.pos.name()))
				.add(FIELDS.due.name(), data.get(FIELDS.due.name()))
				.add(FIELDS.shortUrl.name(), data.get(FIELDS.shortUrl.name()))
				.get();
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.TASKS);
		coll.insert(doc);
	}
	
	public static List <Task> listAll(String boardId){
		List <Task> tasksList = new ArrayList<Task>();
		DBObject query = new BasicDBObject(TaskMongo.FIELDS.idBoard.name(), boardId);
		DBCollection coll = MongoManager.getManager().getCollection(MongoManager.COLLECTIONS.TASKS);
		DBCursor cursor = coll.find(query);
		while (cursor.hasNext()){
			DBObject doc =cursor.next();
			Task  board = TaskMongo.parse(doc);
			tasksList.add(board);
		}
		return tasksList;
	}

}
