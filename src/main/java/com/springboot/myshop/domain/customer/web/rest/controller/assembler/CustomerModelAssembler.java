package com.springboot.myshop.domain.customer.web.rest.controller.assembler;

import com.springboot.myshop.domain.customer.web.rest.controller.response.FoundCustomerResponse;
import com.springboot.myshop.domain.customer.web.rest.controller.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler
		implements RepresentationModelAssembler<FoundCustomerResponse, EntityModel<FoundCustomerResponse>> {

	@Override
	public EntityModel<FoundCustomerResponse> toModel(FoundCustomerResponse entity) {
		
		return EntityModel.of(entity,
				linkTo(methodOn(CustomerController.class).all(null,null)).withRel("all"),
				linkTo(methodOn(CustomerController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(CustomerController.class).update(null, entity.getId())).withRel("update"),
				linkTo(methodOn(CustomerController.class).deleteOne(entity.getId())).withRel("delete")
		);
	}
}
