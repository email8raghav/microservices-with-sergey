package com.springBoot.users.initial.model;

public class AlbumResponseModel {
	
	private String albumId;
	private String name;
	private String description;
	private String userId; 

	public AlbumResponseModel() {
	}

	public AlbumResponseModel(String albumId, String name, String description, String userId) {
		super();
		this.albumId = albumId;
		this.name = name;
		this.description = description;
		this.userId = userId;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
