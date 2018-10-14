package com.nk.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends Exception {

	
	public OrderNotFoundException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

}
