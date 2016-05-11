package com.danigcaff.springframework.samples.spring_web.persistence;

public interface Board extends Entity{
	public String getBoardName();
	public Board setBoardName(String repoId);
	public String getOwner();
	public Board setOwner(String owner);
}
