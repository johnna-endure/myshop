package com.springboot.myshop.domain.order.subdomain.item.entity;

import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

	@Id @GeneratedValue
	@Column(name = "ITEM_ID")
 	private Long id;

	@Setter
	@Column(nullable = false)
	private String name;

	@Setter
	@Column(nullable = false)
	private Integer price;

	@Setter
	@Column(nullable = false)
	private Integer stockQuantity;

	public Item(String name, Integer price, Integer stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public abstract Item update(ItemDto itemDto);
}
