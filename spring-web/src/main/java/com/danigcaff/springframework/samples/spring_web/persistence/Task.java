package com.danigcaff.springframework.samples.spring_web.persistence;

import com.danigcaff.springframework.samples.spring_web.persistence.Task;

public interface Task extends Entity{

	String getDateLastActivity();

	Task setDateLastActivity(String dateLastActivity);

	String getIdBoard();

	Task setIdBoard(String idBoard);

	String getIdList();

	Task setIdList(String idList);

	String getName();

	Task setName(String name);

	String getPos();

	Task setPos(String pos);

	String getDue();

	Task setDue(String due);

	String getShortUrl();

	Task setShortUrl(String shortUrl);

}