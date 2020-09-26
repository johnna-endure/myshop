package com.springboot.myshop.domain.customer.web.rest.controller.assembler;

import com.springboot.myshop.domain.customer.web.rest.controller.CustomerRestController;
import com.springboot.myshop.common.error.response.ErrorResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CustomerErrorResponseModelAssembler
		implements RepresentationModelAssembler<ErrorResponse, EntityModel<ErrorResponse>> {
	@Override
	public EntityModel<ErrorResponse> toModel(ErrorResponse entity) {
		return EntityModel.of(entity,
				linkTo(methodOn(CustomerRestController.class).all(null, null)).withRel("all"));
	}
}
