package com.springboot.myshop.domain.customer.web.rest.controller.advice;

import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.customer.web.rest.controller.CustomerController;
import com.springboot.myshop.web.error.ErrorResponse;
import com.springboot.myshop.web.error.assembler.ErrorResponseModelAssembler;
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
}
