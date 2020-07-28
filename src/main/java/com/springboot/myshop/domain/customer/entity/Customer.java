package com.springboot.myshop.domain.customer.entity;

import com.springboot.myshop.domain.customer.entity.value.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @ToString
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@Entity
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    @CreatedDate
    private LocalDateTime createdDatetime;

    @LastModifiedDate
    private LocalDateTime modifiedDatetime;

    @Builder
    public Customer(String email, String password, Address address) {
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public void update(String password, Address address) {
        if(!Objects.isNull(password)) this.password = password;
        if(!Objects.isNull(password)) this.address = address;
    }
}