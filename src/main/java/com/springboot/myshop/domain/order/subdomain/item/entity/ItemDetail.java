package com.springboot.myshop.domain.order.subdomain.item.entity;

import lombok.*;

import javax.persistence.*;

@Getter @ToString(exclude = "item")
@NoArgsConstructor
@Entity
public class ItemDetail {
	@Id @GeneratedValue
	@Column(name = "ITEM_DETAIL_ID")
	private Long id;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Item item;

	@Column(nullable = false)
	private String tag;

	@Column(nullable = false)
	private String content;

	@Builder
	public ItemDetail(String tag, String content) {
		this.tag = tag;
		this.content = content;
	}
}
