package com.springboot.myshop.domain.customer.web.rest.controller.dto;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.entity.value.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
public class CustomerUpdateDto {
	@NotNull(message = "password is required")
	@Length(min = 8, message = "password is too short, at least 8")
	private final String password;
	@NotNull(message = "address is required")
	private final Address address;

	@Builder
	public CustomerUpdateDto(String password, Address address) {
		this.password = password;
		this.address = address;
	}

	public Customer toEntity() {
		return Customer.builder()
				.address(address)
				.password(password)
				.build();
	}
}
