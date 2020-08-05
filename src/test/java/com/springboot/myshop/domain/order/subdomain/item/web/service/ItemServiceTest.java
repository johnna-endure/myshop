package com.springboot.myshop.domain.order.subdomain.item.web.service;

import com.springboot.myshop.domain.order.subdomain.item.entity.Book;
import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemRepository;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.BookDto;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemRepository itemRepository;
	private BookDto defaultBookDto;

	@BeforeEach
	public void beforeEach() {
		defaultBookDto = BookDto.builder()
				.author("author")
				.isbn("isbn")
				.name("name")
				.price(1000)
				.stockQuantity(1000).build();
	}

	@Test
	public void create_book_반환타입_테스트() {
		Item savedItem = itemService.create(defaultBookDto);
		assertThat(savedItem instanceof Book).isEqualTo(true);
	}

	@Test
	public void create_book_생성_성공한_경우(){
		Item savedItem = itemService.create(defaultBookDto);
		Book book = (Book) itemRepository.findById(savedItem.getId()).get();

		assertThat(book.getName()).isEqualTo("name");
		assertThat(book.getPrice()).isEqualTo(1000);
		assertThat(book.getStockQuantity()).isEqualTo(1000);
		assertThat(book.getAuthor()).isEqualTo("author");
		assertThat(book.getIsbn()).isEqualTo("isbn");
	}

	@Test
	public void findById_book_찾기_성공한_경우() {
		Item savedItem = itemRepository.save(defaultBookDto.toEntity());
		Book book = itemService.findById(savedItem.getId(), Book.class);

		assertThat(book.getName()).isEqualTo("name");
		assertThat(book.getPrice()).isEqualTo(1000);
		assertThat(book.getStockQuantity()).isEqualTo(1000);
		assertThat(book.getAuthor()).isEqualTo("author");
		assertThat(book.getIsbn()).isEqualTo("isbn");
	}

	@Test
	public void update_book_수정_성공한_경우(){
		Item savedItem = itemRepository.save(defaultBookDto.toEntity());
		BookDto priceUpdateDto = BookDto.builder()
				.author(defaultBookDto.getAuthor())
				.isbn(defaultBookDto.getIsbn())
				.name(defaultBookDto.getName())
				.price(5000)
				.stockQuantity(defaultBookDto.getStockQuantity()).build();
		Book updatedBook = itemService.update(savedItem.getId(),priceUpdateDto, Book.class);

		assertThat(updatedBook).isEqualToIgnoringGivenFields(defaultBookDto,"id","price");
		assertThat(updatedBook.getPrice()).isEqualTo(5000);
	}

	@Test
	public void delete_성공한_경우() {
		Item savedItem = itemRepository.save(defaultBookDto.toEntity());

		itemService.delete(savedItem.getId());
		assertThat(itemRepository.existsById(savedItem.getId())).isFalse();
	}

}
