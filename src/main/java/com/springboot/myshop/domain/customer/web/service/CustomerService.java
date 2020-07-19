package com.springboot.myshop.domain.customer.web.service;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.customer.repository.CustomerRepository;
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

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(CustomerUpdateDto updateDto, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomerException(id));
        customer.update(updateDto.getPassword(),updateDto.getAddress());
        return customerRepository.save(customer);
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }
}
