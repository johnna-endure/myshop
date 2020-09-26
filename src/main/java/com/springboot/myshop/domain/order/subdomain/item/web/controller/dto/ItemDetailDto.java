package com.springboot.myshop.domain.order.subdomain.item.web.controller.dto;

import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import lombok.*;

import javax.validation.constraints.Max;

@EqualsAndHashCode
@ToString @NoArgsConstructor
@Getter
public class ItemDetailDto {

	@Max(value = 30, message = "태그가 30자를 초과했습니다.")
	private String tag;
	@Max(value = 200, message = "컨턴츠가 200자를 초과했습니다.")
	private String content;

	@Builder
	public ItemDetailDto(String tag, String content) {
		this.tag = tag;
		this.content = content;
	}

	public ItemDetail toItemDetailEntity() {
		return ItemDetail.builder()
				.tag(tag)
				.content(content)
				.build();
	}
}
