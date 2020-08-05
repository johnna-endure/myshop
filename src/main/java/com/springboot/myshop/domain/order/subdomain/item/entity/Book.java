package com.springboot.myshop.domain.order.subdomain.item.entity;

import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.BookDto;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@ToString
@Getter @Setter
@Entity
@DiscriminatorValue("BOOK")
public class Book extends Item{

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private String isbn;

	@Builder
	public Book(String name, int price, int stockQuantity,String author, String isbn) {
		super(name, price, stockQuantity);
		this.author = author;
		this.isbn = isbn;
	}

	@Override
	public Item update(ItemDto itemDto) {
		BookDto bookDto = (BookDto) itemDto;
		String name = bookDto.getName();
		Integer price = bookDto.getPrice();
		Integer stock_quantity = bookDto.getStockQuantity();
		String author = bookDto.getAuthor();
		String isbn = bookDto.getIsbn();

		if(Objects.nonNull(name)) this.setName(name);
		if(Objects.nonNull(price)) this.setPrice(bookDto.getPrice());
		if(Objects.nonNull(stock_quantity)) this.setStockQuantity(stock_quantity);
		if(Objects.nonNull(author)) this.setAuthor(author);
		if(Objects.nonNull(isbn)) this.setIsbn(isbn);
		return this;
	}
}
