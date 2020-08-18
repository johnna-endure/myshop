package com.springboot.myshop.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {

	private Map<String, String> violations;

	public ValidationException(Map<String, String> violations, String msg) {
		super(msg);
		this.violations = violations;
	}
}
