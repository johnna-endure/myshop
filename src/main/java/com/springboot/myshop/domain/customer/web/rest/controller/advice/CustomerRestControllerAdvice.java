package com.springboot.myshop.domain.customer.web.rest.controller.advice;

import com.springboot.myshop.common.exception.ValidationException;
import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.customer.web.rest.controller.CustomerRestController;
import com.springboot.myshop.common.error.response.ErrorResponse;
import com.springboot.myshop.domain.customer.web.rest.controller.assembler.CustomerErrorResponseModelAssembler;
import com.springboot.myshop.common.error.response.ValidationErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = CustomerRestController.class)
public class CustomerRestControllerAdvice {

	private final CustomerErrorResponseModelAssembler assembler;

	@ExceptionHandler
	public ResponseEntity<EntityModel<ErrorResponse>> notFoundCustomerExceptionHandler(
			HttpServletRequest request, NotFoundCustomerException e){

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("Content-Type","application/json; charset=utf-8")
				.body(assembler.toModel(ErrorResponse.makeErrorResponse(request, e)));
	}

	@ExceptionHandler
	public ResponseEntity<EntityModel<ErrorResponse>> dtoValidationExceptionHandler(
			HttpServletRequest request, ValidationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Content-Type","application/json; charset=utf-8")
				.body(assembler.toModel(ValidationErrorResponse.makeErrorResponse(request, e)));
	}
}
