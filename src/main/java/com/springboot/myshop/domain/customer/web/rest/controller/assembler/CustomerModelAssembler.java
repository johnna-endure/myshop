package com.springboot.myshop.domain.customer.web.rest.controller.assembler;

import com.springboot.myshop.domain.customer.web.rest.controller.dto.FoundCustomerDto;
import com.springboot.myshop.domain.customer.web.rest.controller.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler
		implements RepresentationModelAssembler<FoundCustomerDto, EntityModel<FoundCustomerDto>> {

	@Override
	public EntityModel<FoundCustomerDto> toModel(FoundCustomerDto entity) {
		
		return EntityModel.of(entity,
				linkTo(methodOn(CustomerController.class).all(null,null)).withRel("all"),
				linkTo(methodOn(CustomerController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(CustomerController.class).update(null, entity.getId())).withRel("update"),
				linkTo(methodOn(CustomerController.class).deleteOne(entity.getId())).withRel("delete")
		);
	}
}
