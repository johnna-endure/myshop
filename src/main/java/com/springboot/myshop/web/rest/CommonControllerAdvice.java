package com.springboot.myshop.web.rest;

import com.springboot.myshop.domain.customer.exception.BadCustomerRequestException;
import com.springboot.myshop.web.error.ErrorResponse;
import com.springboot.myshop.web.error.assembler.ErrorResponseModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class CommonControllerAdvice {

	private final ErrorResponseModelAssembler assembler;
	
	/*
	바인딩 실패한 경우 에러 핸들러
	 */
	@ExceptionHandler
	public ResponseEntity<EntityModel<ErrorResponse>> bindingExceptionHandler(
			HttpServletRequest request, MethodArgumentNotValidException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Content-Type","application/json; charset=utf-8")
				.body(assembler.toModel(ErrorResponse.makeErrorResponse(request, e)));
	}

}
