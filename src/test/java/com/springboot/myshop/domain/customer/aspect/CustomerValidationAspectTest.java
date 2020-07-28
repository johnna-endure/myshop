package com.springboot.myshop.domain.customer.aspect;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.entity.repository.CustomerRepository;
import com.springboot.myshop.domain.customer.entity.value.Address;
import com.springboot.myshop.domain.customer.exception.CustomerValidationException;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.web.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
public class CustomerValidationAspectTest {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository repository;

	@Test
	public void validateBeforeCreate_검증통과시() {
		CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
				.email("email")
				.address(new Address("address"))
				.password("password")
				.build();

		assertThat(customerService.create(customerCreateDto))
				.isEqualToComparingOnlyGivenFields(customerCreateDto,"email", "address","password");
	}

	@Test
	public void validateBeforeCreate_이메일_null() {
		CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
				.email(null)
				.address(new Address("address"))
				.password("password")
				.build();

		assertThatThrownBy(() -> customerService.create(customerCreateDto))
				.isExactlyInstanceOf(CustomerValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeCreate_비밀번호_null() {
		CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
				.email("email")
				.address(new Address("address"))
				.password(null)
				.build();

		assertThatThrownBy(() -> customerService.create(customerCreateDto))
				.isExactlyInstanceOf(CustomerValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeCreate_주소_null() {
		CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
				.email("email")
				.address(null)
				.password("password")
				.build();

		assertThatThrownBy(() -> customerService.create(customerCreateDto))
				.isExactlyInstanceOf(CustomerValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeUpdate_검증_통과() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(new Address("address"))
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(new Address("address1"))
				.password("password2")
				.build();

		Customer modifiedCustomer = customerService.update(customerUpdateDto, savedCustomer.getId());
		assertThat(modifiedCustomer.getAddress().getAddress()).isEqualTo(customerUpdateDto.getAddress().getAddress());
		assertThat(modifiedCustomer.getPassword()).isEqualTo(customerUpdateDto.getPassword());
	}

	@Test
	public void validateBeforeUpdate_패스워드_null인_경우() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(new Address("address"))
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(new Address("address1"))
				.password(null)
				.build();

		assertThatThrownBy(() -> customerService.update(customerUpdateDto, savedCustomer.getId()))
				.isExactlyInstanceOf(CustomerValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeUpdate_패스워드_8자미만인_경우() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(new Address("address"))
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(new Address("address1"))
				.password("1234")
				.build();

		assertThatThrownBy(() -> customerService.update(customerUpdateDto, savedCustomer.getId()))
				.isExactlyInstanceOf(CustomerValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeUpdate_주소_null인_경우() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(new Address("address"))
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(null)
				.password("password2")
				.build();

		assertThatThrownBy(() -> customerService.update(customerUpdateDto, savedCustomer.getId()))
				.isExactlyInstanceOf(CustomerValidationException.class)
				.hasMessage("bad request");
	}




}
