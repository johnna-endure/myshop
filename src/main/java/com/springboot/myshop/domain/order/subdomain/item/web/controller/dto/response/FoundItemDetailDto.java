package com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response;

import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter @ToString
public class FoundItemDetailDto {
	private String tag;
	private String content;

	@Builder
	public FoundItemDetailDto(String tag, String content) {
		this.tag = tag;
		this.content = content;
	}

	public static FoundItemDetailDto of(ItemDetail detail) {
		return FoundItemDetailDto.builder()
				.tag(detail.getTag())
				.content(detail.getContent())
				.build();
	}
}
