package com.springboot.myshop.domain.customer.aspect;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.entity.repository.CustomerRepository;
import com.springboot.myshop.domain.value.Address;
import com.springboot.myshop.common.exception.ValidationException;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.web.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
public class CustomerValidationAspectTest {

	private Address defaultAddress = new Address("city", "street", "zipcode");
	private Address newAddress = new Address("city2", "street2", "zipcode2");

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository repository;

	@Test
	public void validateBeforeCreate_검증통과시() {
		CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
				.email("email")
				.address(defaultAddress)
				.password("password")
				.build();

		Customer created = customerService.create(customerCreateDto);
		assertThat(created).isEqualToComparingOnlyGivenFields(customerCreateDto,
						"email","password");
		assertThat(created.getAddress()).isEqualToComparingFieldByField(defaultAddress);
	}

	@Test
	public void validateBeforeCreate_이메일_null() {
		CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
				.email(null)
				.address(defaultAddress)
				.password("password")
				.build();

		assertThatThrownBy(() -> customerService.create(customerCreateDto))
				.isExactlyInstanceOf(ValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeCreate_비밀번호_null() {
		CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
				.email("email")
				.address(defaultAddress)
				.password(null)
				.build();

		assertThatThrownBy(() -> customerService.create(customerCreateDto))
				.isExactlyInstanceOf(ValidationException.class)
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
				.isExactlyInstanceOf(ValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeUpdate_검증_통과() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(defaultAddress)
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(newAddress)
				.password("password2")
				.build();

		Customer modifiedCustomer = customerService.update(customerUpdateDto, savedCustomer.getId());
		assertThat(modifiedCustomer.getAddress()).isEqualToComparingFieldByField(newAddress);
		assertThat(modifiedCustomer.getPassword()).isEqualTo(customerUpdateDto.getPassword());
	}

	@Test
	public void validateBeforeUpdate_패스워드_null인_경우() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(defaultAddress)
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(newAddress)
				.password(null)
				.build();

		assertThatThrownBy(() -> customerService.update(customerUpdateDto, savedCustomer.getId()))
				.isExactlyInstanceOf(ValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeUpdate_패스워드_8자미만인_경우() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(defaultAddress)
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(newAddress)
				.password("1234")
				.build();

		assertThatThrownBy(() -> customerService.update(customerUpdateDto, savedCustomer.getId()))
				.isExactlyInstanceOf(ValidationException.class)
				.hasMessage("bad request");
	}

	@Test
	public void validateBeforeUpdate_주소_null인_경우() {
		Customer customer = Customer.builder()
				.email("email")
				.password("password")
				.address(defaultAddress)
				.build();
		Customer savedCustomer =repository.save(customer);

		CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
				.address(null)
				.password("password2")
				.build();

		assertThatThrownBy(() -> customerService.update(customerUpdateDto, savedCustomer.getId()))
				.isExactlyInstanceOf(ValidationException.class)
				.hasMessage("bad request");
	}
}
