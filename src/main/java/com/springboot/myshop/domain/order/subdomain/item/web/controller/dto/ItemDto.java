package com.springboot.myshop.domain.order.subdomain.item.web.controller.dto;

import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
dtype, name, price, stock_quantity
 */
@ToString
@NoArgsConstructor
@Getter
public class ItemDto {
	@NotNull
	private String name;
	@NotNull
	private Integer price;
	@NotNull
	private Integer stockQuantity;

	private List<ItemDetailDto> details;

	@Builder
	public ItemDto(String name, Integer price, Integer stockQuantity, List<ItemDetailDto> details) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.details = details;
	}

	public Item toItemEntity() {
		return Item.builder()
				.name(name)
				.price(price)
				.stockQuantity(stockQuantity)
				.build();
	}

	public List<ItemDetailDto> getDetails() {
		if(Objects.isNull(details)) return Collections.emptyList();
		return details;
	}

}
