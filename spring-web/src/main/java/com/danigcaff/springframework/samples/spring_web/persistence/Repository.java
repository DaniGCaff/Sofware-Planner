package com.danigcaff.springframework.samples.spring_web.persistence;

public interface Repository extends Entity {
	public String getName();
	public Repository setName(String name);
	public Boolean getAsoc();
	public Repository setAsoc(Boolean asoc);
	public String getBoardId();
	public Repository setBoardId(String boardId);
}
