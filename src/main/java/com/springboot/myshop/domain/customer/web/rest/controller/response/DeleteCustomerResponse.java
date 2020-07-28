package com.springboot.myshop.domain.customer.web.rest.controller.response;

import com.springboot.myshop.domain.customer.web.rest.controller.dto.enums.DeleteResults;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(itemRelation = "result")
public class DeleteCustomerResponse {
	private final DeleteResults result;

	public DeleteCustomerResponse(DeleteResults result) {
		this.result = result;
	}
}
