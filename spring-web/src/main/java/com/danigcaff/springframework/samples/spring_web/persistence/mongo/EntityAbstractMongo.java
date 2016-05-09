package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.mongodb.ReflectionDBObject;

public abstract class EntityAbstractMongo extends ReflectionDBObject implements Entity {

	protected String id;
	protected String creation;
	protected String lastModification;
	
	public EntityAbstractMongo(String id) {
		this.lastModification = this.creation;
		this.id = id;
		this.readById();
	}
	
	public String getId() {
		return id;
	}

	public Entity setId(String id) {
		this.id = id;
		return this;
	}
	
	public String getCreationDate() {
		return creation.toString();
	}

	public Entity setCreationDate(String date) {
		return this;
	}

	public String getLastModificationDate() {
		return lastModification;
	}

	public Entity setLastModificationDate(String date) {
		lastModification = date;
		return this;
	}

	public abstract void save();
	public abstract Entity readById();
}
