package com.springboot.myshop.domain.customer.dto;

import com.springboot.myshop.domain.customer.dto.enums.DeleteResults;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(itemRelation = "result")
public class CustomerDeleteResult {
	private final DeleteResults result;

	public CustomerDeleteResult(DeleteResults result) {
		this.result = result;
	}
}
