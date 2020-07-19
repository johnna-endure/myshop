package com.springboot.myshop.domain.customer.exception;

public class BadCustomerRequestException extends RuntimeException{
	public BadCustomerRequestException(String msg){
		super(msg);
	}
}
