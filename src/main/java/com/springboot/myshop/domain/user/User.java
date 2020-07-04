package com.springboot.myshop.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Column(nullable = false)
    private LocalDateTime createdDatetime;

    @Column(nullable = false)
    private LocalDateTime modifiedDatetime;

    @Builder
    public User(String email, String password,
                Address address, RoleType roleType, LocalDateTime createdDatetime, LocalDateTime modifiedDatetime) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.roleType = roleType;
        this.createdDatetime = createdDatetime;
        this.modifiedDatetime = modifiedDatetime;
    }
}
