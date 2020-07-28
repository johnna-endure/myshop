package com.springboot.myshop.domain.customer.web.rest.controller.dto;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.entity.value.Address;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
public class CustomerCreateDto {
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "password is required")
    @Length(min = 8, message = "password is too short, least 8")
    private String password;
    @NotNull(message = "address is required")
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
