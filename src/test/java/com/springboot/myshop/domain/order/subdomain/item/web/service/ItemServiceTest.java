package com.springboot.myshop.domain.order.subdomain.item.web.service;

import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemDetailRepository;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemRepository;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDetailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemDetailRepository itemDetailRepository;

	@Test
	public void create_성공한_경우() {
		List<ItemDetailDto> details = new ArrayList<>();
		details.add(new ItemDetailDto("tag","content"));
		ItemDto createDto = ItemDto.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.details(details).build();

		Item savedItem  = itemService.create(createDto);
		assertThat(savedItem.getName()).isEqualTo("name");
		assertThat(savedItem.getPrice()).isEqualTo(1000);
		assertThat(savedItem.getStockQuantity()).isEqualTo(1000);

		ItemDetail savedDetail = savedItem.getDetails().get(0);
		assertThat(savedDetail.getTag()).isEqualTo("tag");
		assertThat(savedDetail.getContent()).isEqualTo("content");
	}

	@Test
	public void findById_성공한_경우() {
		//given
		Item item = Item.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.build();
		List<ItemDetail> details = new ArrayList<>();
		details.add(new ItemDetail("tag","content"));
		item.setDetails(details);
		Item savedItem = itemRepository.save(item);

		//when
		Item foundItem = itemService.findOne(savedItem.getId());

		//then
		assertThat(foundItem.getName()).isEqualTo("name");
		assertThat(foundItem.getPrice()).isEqualTo(1000);
		assertThat(foundItem.getStockQuantity()).isEqualTo(1000);

		ItemDetail foundDetail = foundItem.getDetails().get(0);
		assertThat(foundDetail.getTag()).isEqualTo("tag");
		assertThat(foundDetail.getContent()).isEqualTo("content");
	}

	@Test
	public void update_성공한_경우_detailIsNull(){
		//given
		Item item = Item.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.build();
		List<ItemDetail> details = new ArrayList<>();
		details.add(new ItemDetail("tag","content"));
		item.setDetails(details);
		Item savedItem = itemRepository.save(item);

		ItemDto saveDto = ItemDto.builder()
				.name("name1")
				.price(2000)
				.stockQuantity(2000)
				.details(null).build();
		//when
		Item updated = itemService.update(savedItem.getId(), saveDto);
		//then
		assertThat(updated.getDetails()).isNullOrEmpty();
		assertThat(updated.getName()).isEqualTo("name1");
		assertThat(updated.getPrice()).isEqualTo(2000);
		assertThat(updated.getStockQuantity()).isEqualTo(2000);
	}

	@Test
	public void update_성공한_경우_detailIsNotNull(){
		//given
		Item item = Item.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.build();
		List<ItemDetail> details = new ArrayList<>();
		details.add(new ItemDetail("tag","content"));
		item.setDetails(details);
		Item savedItem = itemRepository.save(item);

		List<ItemDetailDto> newDetail = new ArrayList<>();
		newDetail.add(new ItemDetailDto("tag1","content1"));
		newDetail.add(new ItemDetailDto("tag2","content2"));
		ItemDto saveDto = ItemDto.builder()
				.name("name1")
				.price(2000)
				.stockQuantity(2000)
				.details(newDetail).build();
		//when
		Item updated = itemService.update(savedItem.getId(), saveDto);
		//then
		assertThat(updated.getDetails().size()).isEqualTo(2);
		assertThat(updated.getName()).isEqualTo("name1");
		assertThat(updated.getPrice()).isEqualTo(2000);
		assertThat(updated.getStockQuantity()).isEqualTo(2000);
	}

	@Test
	public void delete_성공() {
		//given
		Item item = Item.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.build();
		List<ItemDetail> details = new ArrayList<>();
		details.add(new ItemDetail("tag","content"));
		item.setDetails(details);
		Item savedItem = itemRepository.save(item);
		//사전 조건 체크
		assertThat(itemRepository.existsById(savedItem.getId())).isTrue();
		assertThat(itemDetailRepository.findAll().size()).isEqualTo(1);

		//when
		itemService.delete(savedItem.getId());

		//then
		assertThat(itemRepository.existsById(savedItem.getId())).isFalse();
		assertThat(itemDetailRepository.findAll().size()).isEqualTo(0);
	}
}
