package com.springboot.myshop.domain.customer.aspect;

import com.springboot.myshop.common.exception.ValidationException;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerUpdateDto;
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
public class CustomerValidationAspect {

	private final Validator validator;

	@Pointcut("within(com.springboot.myshop.domain.customer.web.service.CustomerService)")
	public void withinCustomerService(){}

	@Before("withinCustomerService() && execution(* create(*)) && args(customerDto)")
	public void validateBeforeCreate(CustomerCreateDto customerDto) {
		Set<ConstraintViolation<CustomerCreateDto>> constraintViolations = validator.validate(customerDto);

		if(constraintViolations.size() == 0) return;
		throwCustomerValidationException(constraintViolations, CustomerCreateDto.class);
	}

	@Before("withinCustomerService() && execution(* update(*, *)) && args(customerDto, ..)")
	public void validateBeforeUpdate(CustomerUpdateDto customerDto){
		Set<ConstraintViolation<CustomerUpdateDto>> constraintViolations = validator.validate(customerDto);

		if(constraintViolations.size() == 0) return;
		throwCustomerValidationException(constraintViolations, CustomerUpdateDto.class);
	}

	private <T> void throwCustomerValidationException(Set<ConstraintViolation<T>> constraintViolations, Class<T> clazz){
		Map<String, String> violations = new HashMap<>();
		constraintViolations.stream()
				.forEach(constraint ->
						violations.put(constraint.getPropertyPath().toString(), constraint.getMessage()));
		throw new ValidationException(violations, "bad request");
	}
}
