package com.springboot.myshop.domain.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
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

    public void update(String email, String password, Address address) {
        this.email = email;
        this.password = password;
        this.address = address;
    }
}