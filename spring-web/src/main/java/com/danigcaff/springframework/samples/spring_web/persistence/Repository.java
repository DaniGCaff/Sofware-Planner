package com.danigcaff.springframework.samples.spring_web.persistence;

public interface Repository extends Entity {
	public String getRepoId();
	public Repository setRepoId(String repoId);
	public String getRepoName();
	public Repository setRepoName(String repoId);
	public Boolean getAsoc();
	public Repository setAsoc(Boolean asoc);
	public String getOwner();
	public Repository setOwner(String owner);
	public String getBoardId();
	public Repository setBoardId(String boardId);
}
