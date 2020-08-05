package com.springboot.myshop.domain.order.subdomain.item.web.controller;

import com.springboot.myshop.domain.order.subdomain.item.entity.Book;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.BookDto;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import com.springboot.myshop.domain.order.subdomain.item.web.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemRestController {

	private final ItemService itemService;

	@PostMapping("/book/items")
	public String create(BookDto bookDto) {
		return null;
	}
}
