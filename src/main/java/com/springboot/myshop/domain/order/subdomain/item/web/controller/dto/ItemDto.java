package com.springboot.myshop.domain.order.subdomain.item.web.controller.dto;

import com.springboot.myshop.domain.order.subdomain.item.entity.Book;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/*
dtype, name, price, stock_quantity
 */
@Getter
public abstract class ItemDto {

	@NotNull
	private String name;
	@NotNull
	private Integer price;
	@NotNull
	private Integer stockQuantity;

	public ItemDto(String name, Integer price, Integer stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public abstract Book toEntity();
}
