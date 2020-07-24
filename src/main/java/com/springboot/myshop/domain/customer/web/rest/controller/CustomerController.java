package com.springboot.myshop.domain.customer.web.rest.controller;

import com.springboot.myshop.domain.customer.web.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.web.dto.CustomerDeleteResult;
import com.springboot.myshop.domain.customer.web.dto.CustomerResponseDto;
import com.springboot.myshop.domain.customer.web.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.web.dto.enums.DeleteResults;
import com.springboot.myshop.domain.customer.web.rest.controller.assembler.CustomerModelAssembler;
import com.springboot.myshop.domain.customer.web.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<CustomerResponseDto>> all(@RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size) {
        page = Objects.isNull(page) ? 0 : page;
        size = Objects.isNull(size) ? 5 : size;

        List<EntityModel<CustomerResponseDto>> customers = customerService.findAll(page, size).stream()
                .map(customer -> CustomerResponseDto.of(customer))
                .map(responseDto -> assembler.toModel(responseDto))
                .collect(Collectors.toList());
        return CollectionModel.of(customers,
                linkTo(methodOn(CustomerController.class).all(page, size)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).create(null)).withRel("create"));
    }

    @GetMapping("/{id}")
    public EntityModel<CustomerResponseDto> one(@PathVariable Long id) {
        return assembler.toModel(CustomerResponseDto.of(customerService.findOne(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<CustomerResponseDto>> create(@RequestBody CustomerCreateDto customerCreateDto){
        CustomerResponseDto responseDto =
                CustomerResponseDto.of(customerService.create(customerCreateDto.toEntity()));

        return ResponseEntity
                .created(linkTo(methodOn(CustomerController.class).one(responseDto.getId())).toUri())
                .body(assembler.toModel(responseDto));
    }

    /*
    Customer 엔티티의 수정은 password, Address만 가능하다.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerResponseDto>> update(@RequestBody CustomerUpdateDto customerUpdateDto,
                                                   @PathVariable Long id) {
        CustomerResponseDto responseDto = CustomerResponseDto.of(customerService.update(customerUpdateDto, id));

        return ResponseEntity
                .created(linkTo(methodOn(CustomerController.class).one(id)).toUri())
                .body(assembler.toModel(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerDeleteResult>> deleteOne(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EntityModel.of(new CustomerDeleteResult(DeleteResults.SUCCESS),
                        linkTo(methodOn(CustomerController.class).all(null, null)).withRel("all"),
                        linkTo(methodOn(CustomerController.class).deleteOne(id)).withSelfRel()));
    }

}
