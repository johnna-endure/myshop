package com.springboot.myshop.domain.order.subdomain.delivery.entity;

import com.springboot.myshop.domain.value.Address;
import com.springboot.myshop.domain.order.entity.Order;

import javax.persistence.*;

@Entity
public class Delivery {

	@Id @GeneratedValue
	@Column(name = "DELIVERY_ID")
	private Long id;

	@OneToOne(mappedBy = "delivery")
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
}
