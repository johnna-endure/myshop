package com.springboot.myshop.domain;

import com.springboot.myshop.domain.customer.Address;
import com.springboot.myshop.domain.customer.Customer;
import com.springboot.myshop.domain.customer.repository.CustomerRepository;
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
        //given
        Customer customer = new Customer("email", "1234", new Address("address"));

        //when
        Customer savedCustomer = customerRepository.save(customer);

        //then
        assertThat(savedCustomer.getCreatedDatetime()).isNotNull();
        assertThat(savedCustomer.getModifiedDatetime()).isNotNull();
    }

    @Test
    public void 엔티티_업데이트시_auditing_테스트() {
        //given
        Customer customer = new Customer("email", "1234", new Address("address"));
        Customer savedCustomer = customerRepository.save(customer);

        //when
        Customer findCustomer = customerRepository.findById(savedCustomer.getId()).get();
        findCustomer.update("email1", "1234", new Address("address"));
        Customer modified = customerRepository.save(findCustomer);

        assertThat(modified.getCreatedDatetime()).isBefore(modified.getModifiedDatetime());
    }


}
