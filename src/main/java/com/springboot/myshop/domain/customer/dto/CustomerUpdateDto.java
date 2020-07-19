package com.springboot.myshop.domain.customer.dto;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.value.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerUpdateDto {
	private final String password;
	private final Address address;

	public Customer toEntity() {
		return Customer.builder()
				.address(address)
				.password(password)
				.build();
	}
}
