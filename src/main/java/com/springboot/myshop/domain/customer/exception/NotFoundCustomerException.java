package com.springboot.myshop.domain.customer.exception;

public class NotFoundCustomerException extends RuntimeException {
	public NotFoundCustomerException(Long id) {
		super(id + "에 해당하는 회원이 없습니다.");
	}
}
