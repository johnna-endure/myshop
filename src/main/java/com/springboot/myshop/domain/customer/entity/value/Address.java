package com.springboot.myshop.domain.customer.entity.value;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class Address {
    private String address;

    public Address(String address){
        this.address = address;
    }
}
