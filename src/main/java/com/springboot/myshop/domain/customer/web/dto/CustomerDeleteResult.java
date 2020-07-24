package com.springboot.myshop.domain.customer.web.dto;

import com.springboot.myshop.domain.customer.web.dto.enums.DeleteResults;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(itemRelation = "result")
public class CustomerDeleteResult {
	private final DeleteResults result;

	public CustomerDeleteResult(DeleteResults result) {
		this.result = result;
	}
}
