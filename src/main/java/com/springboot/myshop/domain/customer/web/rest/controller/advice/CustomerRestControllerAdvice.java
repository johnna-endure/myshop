package com.springboot.myshop.domain.customer.web.rest.controller.advice;

import com.springboot.myshop.domain.customer.exception.CustomerValidationException;
import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.customer.web.rest.controller.CustomerController;
import com.springboot.myshop.domain.customer.web.rest.controller.response.ErrorResponse;
import com.springboot.myshop.domain.customer.web.rest.controller.assembler.ErrorResponseModelAssembler;
import com.springboot.myshop.domain.customer.web.rest.controller.response.ValidationErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = CustomerController.class)
public class CustomerRestControllerAdvice {

	private final ErrorResponseModelAssembler assembler;

	@ExceptionHandler
	public ResponseEntity<EntityModel<ErrorResponse>> notFoundCustomerExceptionHandler(
			HttpServletRequest request, NotFoundCustomerException e){

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("Content-Type","application/json; charset=utf-8")
				.body(assembler.toModel(ErrorResponse.makeErrorResponse(request, e)));
	}

	@ExceptionHandler
	public ResponseEntity<EntityModel<ErrorResponse>> dtoValidationExceptionHandler(
			HttpServletRequest request, CustomerValidationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Content-Type","application/json; charset=utf-8")
				.body(assembler.toModel(ValidationErrorResponse.makeErrorResponse(request, e)));
	}
}
