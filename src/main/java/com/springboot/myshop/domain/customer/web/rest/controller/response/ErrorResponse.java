package com.springboot.myshop.domain.customer.web.rest.controller.response;

import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class ErrorResponse {
	private String url;
	private String method;
	private String message;

	@Builder
	public ErrorResponse(String url, String method, String message) {
		this.url = url;
		this.method = method;
		this.message = message;
	}

	public static ErrorResponse makeErrorResponse(HttpServletRequest request, Exception e) {
		return ErrorResponse.builder()
				.url(request.getRequestURI())
				.method(request.getMethod())
				.message(e.getMessage())
				.build();
	}
}