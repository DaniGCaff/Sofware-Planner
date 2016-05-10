package com.danigcaff.springframework.samples.spring_web.persistence;

public interface Repository extends Entity {
	public String getName();
	public Repository setName(String name);
	public Boolean getAsoc();
	public Repository setAsoc(Boolean asoc);
	public String getOwner();
	public Repository setOwner(String owner);
	public String getBoardId();
	public Repository setBoardId(String boardId);
}
