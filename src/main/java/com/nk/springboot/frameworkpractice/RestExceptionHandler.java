package com.nk.springboot.frameworkpractice;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nk.springboot.exception.OrderNotFoundException;
import com.nk.springboot.model.ErrorResponse;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(OrderNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException one, WebRequest wq)
	{
		ErrorResponse er = new ErrorResponse().setDetails("").setMessage("Order not found").setTimestamp(new Date());
		return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
	
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public final ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ise, WebRequest wq)
	{
		ErrorResponse er = new ErrorResponse().setDetails("").setMessage("Order is either closed or cancelled").setTimestamp(new Date());
		return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
