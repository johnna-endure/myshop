package com.springboot.myshop.domain.order.subdomain.item.web.service;

import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemDetailRepository;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemRepository;
import com.springboot.myshop.domain.order.subdomain.item.exception.NotFoundItemException;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ItemService {

	private final ItemRepository itemRepository;
	private final ItemDetailRepository itemDetailRepository;

	@Transactional(readOnly = true)
	public Page<Item> findAll(int page, int size) {
		return itemRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	}

	@Transactional(readOnly = true)
	public Item findOne(Long id) {
		return itemRepository.findById(id).orElseThrow(() -> new NotFoundItemException(id));
	}

	public Item create(ItemDto createDto){
		Item savedItem = itemRepository.save(createDto.toItemEntity());
		List<ItemDetail> details = itemDetailRepository.saveAll(createDto.getDetails().stream()
				.map(itemDetailDto -> itemDetailDto.toItemDetailEntity())
				.collect(Collectors.toList()));
		savedItem.setDetails(details);
		return savedItem;
	}

	public Item update(Long id, ItemDto updateDto) {
		Item savedItem = findOne(id);
		return savedItem.update(updateDto);
	}

	public void delete(Long id) {
		itemRepository.deleteById(id);
	}
}
