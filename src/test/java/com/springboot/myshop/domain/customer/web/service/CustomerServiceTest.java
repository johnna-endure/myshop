package com.springboot.myshop.domain.customer.web.service;

import com.springboot.myshop.domain.customer.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.customer.value.Address;
import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.repository.CustomerRepository;
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
                .map(i -> new Customer("email"+i,"1234", new Address("address")))
                .forEach(c -> customerRepository.save(c));

        Page<Customer> firstPage = customerService.findAll(0,3);
        assertThat(firstPage.getNumberOfElements()).isEqualTo(3);

        Page<Customer> secondPage = customerService.findAll(1,3);
        assertThat(secondPage.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    public void findOne_성공한_경우(){
        //given
        Customer customer = new Customer("email", "1234", new Address("address"));
        Customer savedCustomer = customerRepository.save(customer);

        //when
        Customer foundCustomer = customerService.findOne(savedCustomer.getId());

        //then
        assertThat(foundCustomer.getId()).isEqualTo(savedCustomer.getId());
        assertThat(foundCustomer.getEmail()).isEqualTo("email");
        assertThat(foundCustomer.getPassword()).isEqualTo("1234");
        assertThat(foundCustomer.getAddress().getAddress()).isEqualTo("address");
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
        Customer customer = new Customer("email", "1234", new Address("address"));

        //when
        Customer savedCustomer = customerService.create(customer);

        //then
        assertThat(savedCustomer).isEqualToComparingOnlyGivenFields(customer, "email", "password");
        assertThat(savedCustomer.getAddress().getAddress()).isEqualTo(customer.getAddress().getAddress());
        assertThat(savedCustomer.getCreatedDatetime()).isEqualTo(customer.getCreatedDatetime());
        assertThat(savedCustomer.getModifiedDatetime()).isEqualTo(customer.getModifiedDatetime());
    }

    @Test
    public void update_성공한_경우() {
        //given
        Customer customer = new Customer("email", "1234", new Address("address"));
        Long existId = customerRepository.save(customer).getId();

        //when
        CustomerUpdateDto updateDto = new CustomerUpdateDto("modified", new Address("modified"));
        Customer modifiedCustomer = customerService.update(updateDto, existId);

        assertThat(modifiedCustomer.getPassword()).isEqualTo("modified");
        assertThat(modifiedCustomer.getAddress().getAddress()).isEqualTo("modified");
    }

    @Test
    public void update_id에_해당하는_고객_없는_경우_던지는_예외() {
        //given
        CustomerUpdateDto updateDto = new CustomerUpdateDto("modified", new Address("modified"));

        //when and then
        assertThatThrownBy(() -> customerService.update(updateDto, 1l))
                .isExactlyInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    public void delete_삭제_성공한_경우() {
        //given
        Customer customer = new Customer("email", "1234", new Address("address"));
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
