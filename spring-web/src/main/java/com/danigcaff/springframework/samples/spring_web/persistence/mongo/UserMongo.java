package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.persistence.User;

public class UserMongo extends EntityAbstractMongo implements User {

	public UserMongo(String creation, String id) {
		super(creation, id);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public User setName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getGitHubUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public User setGitHubUser(String gitHubUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTrelloUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public User setTrelloUser(String trelloUserId) {
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
