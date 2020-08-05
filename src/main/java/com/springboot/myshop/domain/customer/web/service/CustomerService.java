package com.springboot.myshop.domain.customer.web.service;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.customer.entity.repository.CustomerRepository;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
구현할 기능
- 회원 등록 : create
- 회원 수정 : update
- 회원 정보 조회 : findOne
- 회원 탈퇴 : delete

- 회원 목록 : findCustomers
 */
@RequiredArgsConstructor
@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Page<Customer> findAll(int page, int size) {
        return customerRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @Transactional(readOnly = true)
    public Customer findOne(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomerException(id));
    }

    public Customer create(CustomerCreateDto customerDto) {
        return customerRepository.save(customerDto.toEntity());
    }

    public Customer update(CustomerUpdateDto customerDto, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomerException(id));
        customer.update(customerDto.getPassword(),customerDto.getAddress());
        return customer;
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }
}
