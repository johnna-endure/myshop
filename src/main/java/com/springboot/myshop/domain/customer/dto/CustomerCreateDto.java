package com.springboot.myshop.domain.customer.dto;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.value.Address;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CustomerCreateDto {
    @NotNull(message = "email is null")
    private String email;
    @NotNull(message = "password is null")
    private String password;
    @NotNull(message = "address is null")
    private Address address;

    @Builder
    public CustomerCreateDto(String email, String password, Address address) {
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Customer toEntity() {
        return Customer.builder()
                .email(email)
                .address(address)
                .password(password)
                .build();
    }
}
