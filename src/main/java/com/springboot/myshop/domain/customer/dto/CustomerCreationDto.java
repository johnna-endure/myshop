package com.springboot.myshop.domain.customer.dto;

import com.springboot.myshop.domain.customer.Customer;
import com.springboot.myshop.domain.customer.value.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerCreationDto {
    private final String email;
    private final String password;
    private final Address address;

    public Customer toEntity() {
        return Customer.builder()
                .email(email)
                .address(address)
                .password(password)
                .build();
    }
}
