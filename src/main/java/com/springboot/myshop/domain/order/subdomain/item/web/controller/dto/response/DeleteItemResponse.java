package com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response;

import com.springboot.myshop.common.enums.DeleteResult;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(itemRelation = "result")
public class DeleteItemResponse {
	private DeleteResult result;

	public DeleteItemResponse(DeleteResult result) {
		this.result = result;
	}
}
