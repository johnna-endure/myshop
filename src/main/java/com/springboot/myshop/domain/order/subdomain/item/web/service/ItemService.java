package com.springboot.myshop.domain.order.subdomain.item.web.service;

import com.springboot.myshop.domain.order.subdomain.item.entity.Book;
import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemRepository;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ItemService {

	private final ItemRepository itemRepository;

	public Item create(ItemDto itemDto) {
		return itemRepository.save(itemDto.toEntity());
	}

	public <T extends Item> T findById(Long id, Class<T> type) {
		return (T) itemRepository.findById(id).get();
	}

	public <T extends Item> T update(Long id, ItemDto updateDto, Class<T> type) {
		return (T) findById(id, type).update(updateDto);
	}

	public void delete(Long id) {
		itemRepository.deleteById(id);
	}
}
