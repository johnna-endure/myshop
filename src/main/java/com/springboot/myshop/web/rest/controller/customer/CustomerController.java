package com.springboot.myshop.web.rest.controller.customer;

import com.springboot.myshop.domain.customer.dto.CustomerCreationDto;
import com.springboot.myshop.domain.customer.dto.CustomerResponseDto;
import com.springboot.myshop.domain.customer.dto.CustomerUpdateDto;
import com.springboot.myshop.web.rest.controller.customer.assembler.CustomerModelAssembler;
import com.springboot.myshop.web.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

    //페이징 필요함.
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
                linkTo(methodOn(CustomerController.class).all(page, size)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<CustomerResponseDto> one(@PathVariable Long id) {
        return assembler.toModel(CustomerResponseDto.of(customerService.findOne(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<CustomerResponseDto>> create(@RequestBody CustomerCreationDto customerCreationDto){
        CustomerResponseDto responseDto =
                CustomerResponseDto.of(customerService.create(customerCreationDto.toEntity()));

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
    public CollectionModel<EntityModel<CustomerResponseDto>> deleteOne(@PathVariable Long id) {
        customerService.delete(id);

        return all(0, 5);
    }

}
