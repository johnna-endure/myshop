package com.springboot.myshop.web.rest.controller.customer.assembler;

import com.springboot.myshop.domain.customer.dto.CustomerResponseDto;
import com.springboot.myshop.domain.customer.dto.CustomerUpdateDto;
import com.springboot.myshop.web.rest.controller.customer.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CustomerModelAssembler
		implements RepresentationModelAssembler<CustomerResponseDto, EntityModel<CustomerResponseDto>> {

	@Override
	public EntityModel<CustomerResponseDto> toModel(CustomerResponseDto entity) {

		return EntityModel.of(entity,
				linkTo(methodOn(CustomerController.class).all(null,null)).withRel("all"),
				linkTo(methodOn(CustomerController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(CustomerController.class).create(null)).withRel("create"),
				linkTo(methodOn(CustomerController.class).update(null, entity.getId())).withRel("update"),
				linkTo(methodOn(CustomerController.class).deleteOne(entity.getId())).withRel("delete")
		);
	}
}
