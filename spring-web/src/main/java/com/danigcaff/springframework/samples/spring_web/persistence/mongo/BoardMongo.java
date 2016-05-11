package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import com.danigcaff.springframework.samples.spring_web.persistence.Repository;

public abstract class BoardMongo extends EntityAbstractMongo implements Repository{

	public BoardMongo(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	private String boardName;
	private String owner;

	public enum FIELDS {id, owner, boardName, creation, lastModification}


}
