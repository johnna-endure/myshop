package com.springboot.myshop.common.error.response;

import com.springboot.myshop.common.exception.ValidationException;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Getter
public class ValidationErrorResponse extends ErrorResponse{

	private Map<String, String> violations;

	public ValidationErrorResponse(String url, String method, String message, Map<String, String> violations) {
		super(url, method, message);
		this.violations = violations;
	}

	public static ValidationErrorResponse makeErrorResponse(HttpServletRequest request,
	                                                        ValidationException e) {
		return new ValidationErrorResponse(
				request.getRequestURI(), request.getMethod(), e.getMessage(), e.getViolations());
	}
}
