package com.springboot.myshop.domain.order.subdomain.item.entity;

import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @ToString
@NoArgsConstructor
@Entity
public class Item {

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

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ItemDetail> details = new ArrayList<>();

	@Builder
	public Item(String name, Integer price, Integer stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public Item update(ItemDto itemDto){
		this.name = itemDto.getName();
		this.price = itemDto.getPrice();
		this.stockQuantity = itemDto.getStockQuantity();

		if(itemDto.getDetails() == null){
			this.details = null;
			return this;
		}
 		this.details = itemDto.getDetails().stream()
				.map(d -> d.toItemDetailEntity())
				.collect(Collectors.toList());
		return this;
	}
	/*
	연관관계 설정
	 */
	public void setDetails(List<ItemDetail> details) {
		this.details = details;
		details.stream().forEach(detail -> detail.setItem(this));
	}

	public void addDetail(ItemDetail detail) {
		this.details.add(detail);
		detail.setItem(this);
	}
}
