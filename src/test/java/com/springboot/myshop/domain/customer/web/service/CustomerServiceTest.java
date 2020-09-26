package com.springboot.myshop.domain.customer.web.service;

import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.value.Address;
import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.entity.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class CustomerServiceTest {

    private Address defaultAddress = new Address("city", "street", "zipcode");
    private Address newAddress = new Address("city2", "street2", "zipcode2");
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void beforeEach() {
        customerService = new CustomerService(customerRepository);
    }

    @AfterEach
    public void afterEach(){
        customerRepository.deleteAll();
    }

    @Test
    public void findAll_페이징테스트() {
        //given
        IntStream.range(0,5).boxed()
                .map(i -> new Customer("email"+i,"password", defaultAddress))
                .forEach(c -> customerRepository.save(c));

        Page<Customer> firstPage = customerService.findAll(0,3);
        assertThat(firstPage.getNumberOfElements()).isEqualTo(3);

        Page<Customer> secondPage = customerService.findAll(1,3);
        assertThat(secondPage.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    public void findOne_성공한_경우(){
        //given
        Customer customer = new Customer("email", "password",defaultAddress);
        Customer savedCustomer = customerRepository.save(customer);

        //when
        Customer foundCustomer = customerService.findOne(savedCustomer.getId());

        //then
        assertThat(foundCustomer.getId()).isEqualTo(savedCustomer.getId());
        assertThat(foundCustomer.getEmail()).isEqualTo("email");
        assertThat(foundCustomer.getPassword()).isEqualTo("password");
        assertThat(foundCustomer.getAddress()).isEqualToComparingFieldByField(defaultAddress);
    }

    @Test
    public void findOne_없는_고객_조회시_던지는_예외() {
        //when and then
        assertThatThrownBy(() -> customerService.findOne(1l))
                .isExactlyInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    public void create_가입에_성공한_경우(){
        //given
        CustomerCreateDto customerDto =
                new CustomerCreateDto("email", "password", defaultAddress);

        //when
        Customer savedCustomer = customerService.create(customerDto);

        //then
        assertThat(savedCustomer).isEqualToComparingOnlyGivenFields(customerDto, "email", "password");
        assertThat(savedCustomer.getAddress()).isEqualToComparingFieldByField(defaultAddress);
        assertThat(savedCustomer.getCreatedDatetime()).isNotNull();
        assertThat(savedCustomer.getModifiedDatetime()).isNotNull();
    }

    @Test
    public void update_성공한_경우() {
        //given
        Customer customer = new Customer("email", "password", defaultAddress);
        Long existId = customerRepository.save(customer).getId();

        //when

        CustomerUpdateDto updateDto = new CustomerUpdateDto("password2", newAddress);
        Customer modifiedCustomer = customerService.update(updateDto, existId);

        assertThat(modifiedCustomer.getPassword()).isEqualTo("password2");
        assertThat(modifiedCustomer.getAddress()).isEqualToComparingFieldByField(newAddress);
    }

    @Test
    public void update_id에_해당하는_고객_없는_경우_던지는_예외() {
        //given
        CustomerUpdateDto updateDto = new CustomerUpdateDto("modified", newAddress);

        //when and then
        assertThatThrownBy(() -> customerService.update(updateDto, 1l))
                .isExactlyInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    public void delete_삭제_성공한_경우() {
        //given
        Customer customer = new Customer("email", "1234", defaultAddress);
        Customer savedCustomer = customerRepository.save(customer);

        //when
        customerService.delete(savedCustomer.getId());

        //then
        assertThat(customerRepository.findById(savedCustomer.getId())).isEqualTo(Optional.empty());
    }

    @Test
    public void delete_없는_고객_삭제시() {
        //when and then
        assertThatThrownBy(() -> customerService.delete(1l))
                .isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }
}
