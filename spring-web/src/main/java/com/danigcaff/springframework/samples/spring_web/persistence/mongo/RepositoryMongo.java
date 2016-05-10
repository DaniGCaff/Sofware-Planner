package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.persistence.Repository;

public class RepositoryMongo extends EntityAbstractMongo implements Repository {

	private String name;
	private String owner;
	private String boardId;
	private Boolean asoc;

	public RepositoryMongo(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public Repository setName(String name) {
		this.name = name;
		return this;
	}

	public Boolean getAsoc() {
		return asoc;
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
		// TODO Auto-generated method stub
	}

	@Override
	public Entity readById() {
		// TODO Auto-generated method stub
		return null;
	}

}
