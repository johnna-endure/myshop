package com.springboot.myshop.domain.order.entity;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.item.entity.Item;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

public class Orders {

	private Long id;
	private Customer customer;
	private List<Item> items;

	private int totalAmount;

	@CreatedDate
	private LocalDateTime createdDatetime;

	@LastModifiedDate
	private LocalDateTime modifiedDatetime;
}
