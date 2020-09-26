package com.springboot.myshop.domain.order.subdomain.item.web.assembler;

import com.springboot.myshop.domain.order.subdomain.item.web.controller.ItemRestController;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response.FoundItemDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemModelAssembler
		implements RepresentationModelAssembler<FoundItemDto, EntityModel<FoundItemDto>> {

	@Override
	public EntityModel<FoundItemDto> toModel(FoundItemDto entity) {
		return EntityModel.of(entity,
				linkTo(methodOn(ItemRestController.class).all(null, null)).withRel("all"),
				linkTo(methodOn(ItemRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(ItemRestController.class).update(entity.getId(), null)).withRel("update"),
				linkTo(methodOn(ItemRestController.class).deleteOne(entity.getId())).withRel("delete")
		);
	}
}
