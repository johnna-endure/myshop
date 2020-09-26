package com.springboot.myshop.domain.customer.web.rest.controller;

import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.response.DeleteCustomerResponse;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.response.FoundCustomerDto;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.CustomerUpdateDto;
import com.springboot.myshop.common.enums.DeleteResult;
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
public class CustomerRestController {

    private final CustomerService customerService;
    private final CustomerModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<FoundCustomerDto>> all(@RequestParam(required = false) Integer page,
                                                              @RequestParam(required = false) Integer size) {
        page = Objects.isNull(page) ? 0 : page;
        size = Objects.isNull(size) ? 5 : size;

        List<EntityModel<FoundCustomerDto>> customers = customerService.findAll(page, size).stream()
                .map(customer -> FoundCustomerDto.of(customer))
                .map(responseDto -> assembler.toModel(responseDto))
                .collect(Collectors.toList());
        return CollectionModel.of(customers,
                linkTo(methodOn(CustomerRestController.class).all(page, size)).withSelfRel(),
                linkTo(methodOn(CustomerRestController.class).create(null)).withRel("create"));
    }

    @GetMapping("/{id}")
    public EntityModel<FoundCustomerDto> one(@PathVariable Long id) {
        return assembler.toModel(FoundCustomerDto.of(customerService.findOne(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<FoundCustomerDto>> create(@RequestBody CustomerCreateDto customerCreateDto){
        FoundCustomerDto responseDto =
                FoundCustomerDto.of(customerService.create(customerCreateDto));

        return ResponseEntity
                .created(linkTo(methodOn(CustomerRestController.class).one(responseDto.getId())).toUri())
                .body(assembler.toModel(responseDto));
    }

    /*
    Customer 엔티티의 수정은 password, Address만 가능하다.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FoundCustomerDto>> update(
            @RequestBody CustomerUpdateDto customerUpdateDto, @PathVariable Long id) {
        FoundCustomerDto responseDto =
                FoundCustomerDto.of(customerService.update(customerUpdateDto, id));

        return ResponseEntity
                .created(linkTo(methodOn(CustomerRestController.class).one(id)).toUri())
                .body(assembler.toModel(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<DeleteCustomerResponse>> deleteOne(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EntityModel.of(new DeleteCustomerResponse(DeleteResult.SUCCESS),
                        linkTo(methodOn(CustomerRestController.class).all(null, null)).withRel("all"),
                        linkTo(methodOn(CustomerRestController.class).deleteOne(id)).withSelfRel()));
    }

}
