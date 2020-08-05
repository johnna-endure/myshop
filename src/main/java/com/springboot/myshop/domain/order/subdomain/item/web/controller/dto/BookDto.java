package com.springboot.myshop.domain.order.subdomain.item.web.controller.dto;

import com.springboot.myshop.domain.order.subdomain.item.entity.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Getter
public class BookDto extends ItemDto {

	@NotNull
	private String author;
	@NotNull
	private String isbn;

	@Builder
	public BookDto(String name, int price, int stockQuantity, String author, String isbn) {
		super(name, price, stockQuantity);
		this.author = author;
		this.isbn = isbn;
	}

	@Override
	public Book toEntity() {
		return Book.builder()
				.name(getName())
				.price(getPrice())
				.stockQuantity(getStockQuantity())
				.author(author)
				.isbn(isbn).build();
	}
}
