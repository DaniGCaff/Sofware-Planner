package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.persistence.Repository;

public class RepositoryMongo extends EntityAbstractMongo implements Repository {

	private String name;
	private Boolean asoc;
	
	public RepositoryMongo(String id, String name) {
		super(id);
		this.name = name;
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

	public String getBoardId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Repository setBoardId(String boardId) {
		// TODO Auto-generated method stub
		return null;
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
