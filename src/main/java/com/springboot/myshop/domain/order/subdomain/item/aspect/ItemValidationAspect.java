package com.springboot.myshop.domain.order.subdomain.item.aspect;

import com.springboot.myshop.common.exception.ValidationException;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@RequiredArgsConstructor
@Component
@Aspect
public class ItemValidationAspect {

	private final Validator validator;

	@Pointcut("within(com.springboot.myshop.domain.order.subdomain.item.web.service.ItemService)")
	public void withinItemSerivce(){}

	@Before("withinItemSerivce() && execution(* create(*)) && args(itemDto)")
	public void validateBeforeCreate(ItemDto itemDto) {
		Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);

		if(violations.size() == 0) return;
		throwItemValidationException(violations);
	}

	@Before("withinItemSerivce() && execution(* update(*,*)) && args(.. ,itemDto)")
	public void validateBeforeUpdate(ItemDto itemDto) {
		Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);

		if(violations.size() == 0) return;
		throwItemValidationException(violations);
	}

	private <T> void throwItemValidationException(Set<ConstraintViolation<ItemDto>> constraintViolations){
		Map<String, String> violations = new HashMap<>();
		constraintViolations.stream()
				.forEach(constraint ->
						violations.put(constraint.getPropertyPath().toString(), constraint.getMessage()));
		throw new ValidationException(violations, "bad request");
	}
}
