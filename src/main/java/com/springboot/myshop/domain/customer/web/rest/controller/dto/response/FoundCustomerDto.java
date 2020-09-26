package com.springboot.myshop.domain.customer.web.rest.controller.dto.response;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.value.Address;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Relation(collectionRelation = "customers", itemRelation = "customer")
public class FoundCustomerDto {
    private Long id;
    private String email;
    private Address address;
    private LocalDateTime createdDatetime;
    private LocalDateTime modifiedDatetime;

    public static FoundCustomerDto of(Customer customer) {
        return new FoundCustomerDto(customer);
    }

    public FoundCustomerDto(Customer customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.createdDatetime = customer.getCreatedDatetime();
        this.modifiedDatetime = customer.getModifiedDatetime();
    }
}
