package com.springboot.myshop.domain.order.subdomain.item.exception;

public class NotFoundItemException extends RuntimeException{
	public NotFoundItemException(String msg) {
		super(msg);
	}
}
