package com.nk.springboot.model;

import java.util.Date;

public class ErrorResponse {

	Date timestamp;
	String message, details;
	
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public ErrorResponse setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ErrorResponse setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public String getDetails() {
		return details;
	}
	
	public ErrorResponse setDetails(String details) {
		this.details = details;
		return this;
	}
	
}
