package com.danigcaff.springframework.samples.spring_web.persistence.mongo;

import com.danigcaff.springframework.samples.spring_web.persistence.Entity;
import com.danigcaff.springframework.samples.spring_web.persistence.Task;

public class TaskMongo extends EntityAbstract implements Task {

	public TaskMongo(String creation, String id) {
		super(creation, id);
		// TODO Auto-generated constructor stub
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
