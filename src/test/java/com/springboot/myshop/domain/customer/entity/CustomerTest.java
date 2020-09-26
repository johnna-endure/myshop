package com.springboot.myshop.domain.customer.entity;

import com.springboot.myshop.domain.value.Address;
import com.springboot.myshop.domain.customer.entity.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    public void afterEach() {
        customerRepository.deleteAll();
    }

    @Test
    public void entityManagerIsNotNull() {
        assertThat(customerRepository).isNotNull();
    }

    @Test
    public void 엔티티_등록시_auditing_테스트() {
        Address address = Address.builder()
                .city("city")
                .street("street")
                .zipcode("zipcode")
                .build();
        //given
        Customer customer = new Customer("email", "1234", address);

        //when
        Customer savedCustomer = customerRepository.save(customer);

        //then
        assertThat(savedCustomer.getCreatedDatetime()).isNotNull();
        assertThat(savedCustomer.getModifiedDatetime()).isNotNull();
    }

    @Test
    public void 엔티티_업데이트시_auditing_테스트() {
        //given
        Customer customer = new Customer("email", "1234",
                new Address("city", "street", "zipcode"));
        Customer savedCustomer = customerRepository.save(customer);

        //when
        savedCustomer.update("modified", new Address("city2", "street2", "zipcode2"));
        Customer modifiedCustomer = customerRepository.save(savedCustomer);

        assertThat(modifiedCustomer.getCreatedDatetime()).isBefore(modifiedCustomer.getModifiedDatetime());
    }
}
