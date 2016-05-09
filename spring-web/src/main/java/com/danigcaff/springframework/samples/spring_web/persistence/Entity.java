package com.danigcaff.springframework.samples.spring_web.persistence;

public interface Entity {
	public String getId();
	public Entity setId(String id);
	public String getCreationDate();
	public Entity setCreationDate(String date);
	public String getLastModificationDate();
	public Entity setLastModificationDate(String date);
	public void save();
	Entity readById();
}
