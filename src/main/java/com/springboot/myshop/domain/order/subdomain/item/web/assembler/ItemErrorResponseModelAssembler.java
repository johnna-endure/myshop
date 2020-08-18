package com.springboot.myshop.domain.order.subdomain.item.web.assembler;

import com.springboot.myshop.common.error.response.ErrorResponse;
import com.springboot.myshop.domain.customer.web.rest.controller.CustomerRestController;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.ItemRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemErrorResponseModelAssembler
		implements RepresentationModelAssembler<ErrorResponse, EntityModel<ErrorResponse>> {

	@Override
	public EntityModel<ErrorResponse> toModel(ErrorResponse entity) {
		return EntityModel.of(entity,
				linkTo(methodOn(ItemRestController.class).all(null, null)).withRel("all"));
	}
}
