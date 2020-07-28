package com.springboot.myshop.domain.customer.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomerValidationException extends RuntimeException{

	private Map<String, String> violations;

	public CustomerValidationException(Map<String, String> violations, String msg) {
		super(msg);
		this.violations = violations;
	}
}
