package com.springboot.myshop.domain.order.subdomain.item.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/*
일단은 보류.
 */
@Entity
public class Category {

	@Id @GeneratedValue
	@Column(name = "CATEGORY_ID")
	private Long id;

	private String name;

	@ManyToMany
	@JoinTable(name = "CATEGORY_ITEM",
			joinColumns = @JoinColumn(name = "CATEGORY_ID"),
			inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
	private List<Item> items = new ArrayList<>();
	
	//연관관계 메서드 보류
}
