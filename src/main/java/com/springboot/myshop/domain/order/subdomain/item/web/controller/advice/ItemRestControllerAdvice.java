package com.springboot.myshop.domain.order.subdomain.item.web.controller.advice;

import com.springboot.myshop.common.error.response.ErrorResponse;
import com.springboot.myshop.common.error.response.ValidationErrorResponse;
import com.springboot.myshop.common.exception.ValidationException;
import com.springboot.myshop.domain.order.subdomain.item.exception.NotFoundItemException;
import com.springboot.myshop.domain.order.subdomain.item.web.assembler.ItemErrorResponseModelAssembler;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.ItemRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = ItemRestController.class)
public class ItemRestControllerAdvice {
	private final ItemErrorResponseModelAssembler assembler;

	@ExceptionHandler
	public ResponseEntity<EntityModel<ErrorResponse>> notFoundItemExceptionHandler(
			HttpServletRequest request, NotFoundItemException e){

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
