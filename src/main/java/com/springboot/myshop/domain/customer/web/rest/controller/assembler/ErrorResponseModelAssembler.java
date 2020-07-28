package com.springboot.myshop.domain.customer.web.rest.controller.assembler;

import com.springboot.myshop.domain.customer.web.rest.controller.CustomerController;
import com.springboot.myshop.domain.customer.web.rest.controller.response.ErrorResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ErrorResponseModelAssembler
		implements RepresentationModelAssembler<ErrorResponse, EntityModel<ErrorResponse>> {
	@Override
	public EntityModel<ErrorResponse> toModel(ErrorResponse entity) {
		return EntityModel.of(entity,
				linkTo(methodOn(CustomerController.class).all(null, null)).withRel("all"));
	}
}
