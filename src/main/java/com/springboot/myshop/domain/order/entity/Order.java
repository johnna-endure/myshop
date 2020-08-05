package com.springboot.myshop.domain.order.entity;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.order.subdomain.delivery.entity.Delivery;
import com.springboot.myshop.domain.order.subdomain.orderitem.entity.OrderItem;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "ORDERS")
public class Order {

	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;

	private LocalDateTime orderDate;

	@Enumerated
	private OrderStatus status;

	//연관 관계 메서드는 나중에
}
