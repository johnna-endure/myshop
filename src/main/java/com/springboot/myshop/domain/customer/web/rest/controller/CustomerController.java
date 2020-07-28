package com.springboot.myshop.domain.customer.web.rest.controller;

import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.web.rest.controller.response.DeleteCustomerResponse;
import com.springboot.myshop.domain.customer.web.rest.controller.response.FoundCustomerResponse;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.enums.DeleteResults;
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
    public CollectionModel<EntityModel<FoundCustomerResponse>> all(@RequestParam(required = false) Integer page,
                                                                   @RequestParam(required = false) Integer size) {
        page = Objects.isNull(page) ? 0 : page;
        size = Objects.isNull(size) ? 5 : size;

        List<EntityModel<FoundCustomerResponse>> customers = customerService.findAll(page, size).stream()
                .map(customer -> FoundCustomerResponse.of(customer))
                .map(responseDto -> assembler.toModel(responseDto))
                .collect(Collectors.toList());
        return CollectionModel.of(customers,
                linkTo(methodOn(CustomerController.class).all(page, size)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).create(null)).withRel("create"));
    }

    @GetMapping("/{id}")
    public EntityModel<FoundCustomerResponse> one(@PathVariable Long id) {
        return assembler.toModel(FoundCustomerResponse.of(customerService.findOne(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<FoundCustomerResponse>> create(@RequestBody CustomerCreateDto customerCreateDto){
        FoundCustomerResponse responseDto =
                FoundCustomerResponse.of(customerService.create(customerCreateDto));

        return ResponseEntity
                .created(linkTo(methodOn(CustomerController.class).one(responseDto.getId())).toUri())
                .body(assembler.toModel(responseDto));
    }

    /*
    Customer 엔티티의 수정은 password, Address만 가능하다.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FoundCustomerResponse>> update(
            @RequestBody CustomerUpdateDto customerUpdateDto, @PathVariable Long id) {
        FoundCustomerResponse responseDto =
                FoundCustomerResponse.of(customerService.update(customerUpdateDto, id));

        return ResponseEntity
                .created(linkTo(methodOn(CustomerController.class).one(id)).toUri())
                .body(assembler.toModel(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<DeleteCustomerResponse>> deleteOne(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EntityModel.of(new DeleteCustomerResponse(DeleteResults.SUCCESS),
                        linkTo(methodOn(CustomerController.class).all(null, null)).withRel("all"),
                        linkTo(methodOn(CustomerController.class).deleteOne(id)).withSelfRel()));
    }

}
