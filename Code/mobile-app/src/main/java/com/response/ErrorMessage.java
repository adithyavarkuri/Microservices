package com.response;

import java.util.Date;

public class ErrorMessage {
	
	public ErrorMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public ErrorMessage(Date date, String msg) {
		this.timestamp = date;
		this.message = msg;
	}
	
	
	private Date timestamp;
	private String message;
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
