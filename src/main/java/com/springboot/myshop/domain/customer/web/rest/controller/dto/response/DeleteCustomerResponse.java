package com.springboot.myshop.domain.customer.web.rest.controller.dto.response;

import com.springboot.myshop.common.enums.DeleteResult;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(itemRelation = "result")
public class DeleteCustomerResponse {
	private final DeleteResult result;

	public DeleteCustomerResponse(DeleteResult result) {
		this.result = result;
	}
}
