package com.springboot.myshop.domain.customer.web.rest.controller.response;

import com.springboot.myshop.domain.customer.exception.CustomerValidationException;
import lombok.Builder;
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
	                                                        CustomerValidationException e) {
		return new ValidationErrorResponse(
				request.getRequestURI(), request.getMethod(), e.getMessage(), e.getViolations());
	}
}
