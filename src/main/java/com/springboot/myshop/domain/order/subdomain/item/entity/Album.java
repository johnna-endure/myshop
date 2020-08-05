package com.springboot.myshop.domain.order.subdomain.item.entity;

import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter @Setter
@Entity
@DiscriminatorValue("ALBUM")
public class Album extends Item{

	@Column(nullable = false)
	private String artist;
	private String description;

	@Override
	public Item update(ItemDto itemDto) {
		return null;
	}
}
