package com.springBoot.users.initial.exception;

import java.util.Date;

public class ErrorMessage {
	
	private Date timestamp;
	private String meassage;
	private String description;

	public ErrorMessage(){
		
	}
	
	public ErrorMessage(Date timestamp, String meassage, String description){
		this.timestamp=timestamp;
		this.meassage=meassage;
		this.description=description;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMeassage() {
		return meassage;
	}

	public void setMeassage(String meassage) {
		this.meassage = meassage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
