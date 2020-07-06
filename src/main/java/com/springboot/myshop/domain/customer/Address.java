package com.springboot.myshop.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@RequiredArgsConstructor
@Embeddable
public class Address {
    private final String address;
}
