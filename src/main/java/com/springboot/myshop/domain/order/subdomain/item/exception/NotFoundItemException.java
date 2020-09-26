package com.springboot.myshop.domain.order.subdomain.item.exception;

public class NotFoundItemException extends RuntimeException{
	public NotFoundItemException(Long id) {
		super("해당 아이템을 찾을 수 없습니다 : " + id);
	}
}
