package com.springboot.myshop.domain.order.subdomain.item.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.springboot.myshop.domain.customer.web.rest.controller.dto.response.FoundCustomerDto;
import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemRepository;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDetailDto;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response.DeleteItemResponse;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response.FoundItemDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.ManifestDigester;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.springboot.myshop.jsonpath.JsonPathUtil.findElement;
import static com.springboot.myshop.jsonpath.JsonPathUtil.findListElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ItemRestControllerTest {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ItemRestController itemRestController;
	private Gson gson = new Gson();

	@Test
	public void all_성공_noParam_5개이상_페이징테스트() throws Exception {
		saveAndFlushDummyItems(6);

		String content = mockMvc.perform(
				get("/items")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		List<Object> page = findListElement(content, "$..items.*");
		assertThat(page.size()).isEqualTo(5);
	}

	@Test
	public void all_성공_noParam_5개이하_페이징테스트() throws Exception {
		saveAndFlushDummyItems(3);

		String content = mockMvc.perform(
				get("/items")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		List<Object> page = findListElement(content, "$..items.*");
		assertThat(page.size()).isEqualTo(3);
	}

	@Test
	public void all_성공_sizeParam_페이징테스트() throws Exception {
		saveAndFlushDummyItems(5);

		String firstPageContent = mockMvc.perform(
				get("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.param("size", "3"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		List<Object> firstPage = findListElement(firstPageContent, "$..items.*");
		assertThat(firstPage.size()).isEqualTo(3);

		String secondPageContent = mockMvc.perform(
				get("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.param("size", "3")
						.param("page", "1"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		List<Object> secondPage = findListElement(secondPageContent, "$..items.*");
		assertThat(secondPage.size()).isEqualTo(2);
	}

	@Test
	public void all_링크테스트(){
		CollectionModel<EntityModel<FoundItemDto>> collectionModel= itemRestController.all(null, null);
		assertThat(collectionModel.getLink("self").get().toUri().getPath()).isEqualTo("/items");
		assertThat(collectionModel.getLink("create").get().toUri().getPath()).isEqualTo("/items");
	}

	@Test
	public void one_성공_엔티티테스트() throws Exception {
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));
		String content = mockMvc.perform(
				get("/items/"+saved.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(findElement(content, "$.name",String.class)).isEqualTo("name0");
		assertThat(findElement(content, "$.price",Integer.class)).isEqualTo(1000);
		assertThat(findElement(content, "$.stockQuantity",Integer.class)).isEqualTo(1000);
		assertThat(findElement(content, "$.details[0].tag",String.class)).isEqualTo("tag0");
		assertThat(findElement(content, "$.details[0].content",String.class)).isEqualTo("content0");
	}

	@Test
	public void one_성공_링크테스트(){
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));
		EntityModel<FoundItemDto> entityModel = itemRestController.one(saved.getId());

		assertThat(entityModel.getLink("all").get().toUri().getPath()).isEqualTo("/items");
		assertThat(entityModel.getLink("self").get().toUri().getPath()).isEqualTo("/items/"+saved.getId());
		assertThat(entityModel.getLink("update").get().toUri().getPath()).isEqualTo("/items/"+saved.getId());
		assertThat(entityModel.getLink("delete").get().toUri().getPath()).isEqualTo("/items/"+saved.getId());
	}

	@Test
	public void create_파라미터_바인딩테스트() throws Exception {
		List<ItemDetailDto> details = new ArrayList<>();
		details.add(new ItemDetailDto("tag","content"));
		ItemDto createDto = ItemDto.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.details(details).build();

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
		Object ret =  converter.read(ItemDto.class, new MockHttpInputMessage(gson.toJson(createDto).getBytes()));
		ItemDto bindedDto = (ItemDto) ret;
		assertThat(bindedDto.getName()).isEqualTo("name");
		assertThat(bindedDto.getPrice()).isEqualTo(1000);
		assertThat(bindedDto.getStockQuantity()).isEqualTo(1000);
		assertThat(bindedDto.getDetails()).isEqualTo(details);
	}

	@Test
	public void create_바인딩_실패시() throws Exception {
		ItemDto itemDto = ItemDto.builder()
				.name(null)
				.stockQuantity(null)
				.price(null)
				.details(null)
				.build();

		String content = mockMvc.perform(
				post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(itemDto)))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();

		assertThat(findElement(content, "$.url", String.class)).isEqualTo("/items");
		assertThat(findElement(content, "$.method", String.class)).isEqualTo("POST");
		assertThat(findElement(content, "$.message", String.class)).isEqualTo("bad request");
		assertThat(findElement(content, "$.violations.name", String.class)).isEqualTo("must not be null");
		assertThat(findElement(content, "$.violations.price", String.class)).isEqualTo("must not be null");
		assertThat(findElement(content, "$.violations.stockQuantity", String.class)).isEqualTo("must not be null");
	}

	@Test
	public void create_성공한_경우_item엔티티_응답테스트() throws Exception {
		List<ItemDetailDto> details = new ArrayList<>();
		details.add(new ItemDetailDto("tag","content"));
		ItemDto createDto = ItemDto.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.details(details).build();

		String content =mockMvc.perform(
				post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(createDto)))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();

		assertThat(findElement(content, "$.name", String.class)).isEqualTo("name");
		assertThat(findElement(content, "$.price", Integer.class)).isEqualTo(1000);
		assertThat(findElement(content, "$.stockQuantity", Integer.class)).isEqualTo(1000);
		assertThat(findElement(content, "$.details[0].tag", String.class)).isEqualTo("tag");
		assertThat(findElement(content, "$.details[0].content", String.class)).isEqualTo("content");
	}

	@Test
	public void create_성공한_경우_item엔티티_detailsIsNull_응답테스트() throws Exception {
		List<ItemDetailDto> details = new ArrayList<>();
		details.add(new ItemDetailDto("tag","content"));
		ItemDto createDto = ItemDto.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.details(null).build();

		String content =mockMvc.perform(
				post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(createDto)))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();
		System.out.println(content);
		assertThat(findElement(content, "$.name", String.class)).isEqualTo("name");
		assertThat(findElement(content, "$.price", Integer.class)).isEqualTo(1000);
		assertThat(findElement(content, "$.stockQuantity", Integer.class)).isEqualTo(1000);
	}

	@Test
	public void create_성공한_경우_링크_테스트() {
		ItemDto itemDto = ItemDto.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.details(new ArrayList<>())
				.build();
		ResponseEntity responseEntity = itemRestController.create(itemDto);
		EntityModel<FoundCustomerDto> entityModel = (EntityModel<FoundCustomerDto>) responseEntity.getBody();
		assertThat(entityModel.hasLink("all")).isTrue();
		assertThat(entityModel.hasLink("update")).isTrue();
		assertThat(entityModel.hasLink("delete")).isTrue();
		assertThat(entityModel.hasLink("self")).isTrue();
	}

	@SneakyThrows
	@Test
	public void update_바인딩_실패시() throws Exception {
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));

		ItemDto itemDto = ItemDto.builder()
				.name(null)
				.stockQuantity(null)
				.price(null)
				.details(null)
				.build();

		String content = mockMvc.perform(
				put("/items/"+saved.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(itemDto)))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();

		assertThat(findElement(content, "$.url", String.class)).isEqualTo("/items/"+saved.getId());
		assertThat(findElement(content, "$.method", String.class)).isEqualTo("PUT");
		assertThat(findElement(content, "$.message", String.class)).isEqualTo("bad request");
		assertThat(findElement(content, "$.violations.name", String.class)).isEqualTo("must not be null");
		assertThat(findElement(content, "$.violations.price", String.class)).isEqualTo("must not be null");
		assertThat(findElement(content, "$.violations.stockQuantity", String.class)).isEqualTo("must not be null");
	}

	@Test
	public void update_성공_엔티티테스트() throws Exception {
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));

		List<ItemDetailDto> detailDtos = new ArrayList<>();
		detailDtos.add(new ItemDetailDto("tag1", "content1"));
		ItemDto updateDto = ItemDto.builder()
				.name("name1")
				.price(500)
				.stockQuantity(500)
				.details(detailDtos)
				.build();

		String content = mockMvc.perform(
			put("/items/"+saved.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(updateDto)))
			.andExpect(status().isCreated())
			.andReturn().getResponse().getContentAsString();

		assertThat(findElement(content, "$.name", String.class)).isEqualTo("name1");
		assertThat(findElement(content, "$.price", Integer.class)).isEqualTo(500);
		assertThat(findElement(content, "$.stockQuantity", Integer.class)).isEqualTo(500);
		assertThat(findElement(content, "$.details[0].tag", String.class)).isEqualTo("tag1");
		assertThat(findElement(content, "$.details[0].content", String.class)).isEqualTo("content1");
	}

	@Test
	public void update_성공_detailsIsNull_엔티티테스트() throws Exception {
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));

		ItemDto updateDto = ItemDto.builder()
				.name("name1")
				.price(500)
				.stockQuantity(500)
				.details(null)
				.build();

		String content = mockMvc.perform(
				put("/items/"+saved.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(updateDto)))
				.andExpect(status().isCreated())
				.andExpect(redirectedUrl("http://localhost/items/"+saved.getId()))
				.andReturn().getResponse().getContentAsString();

		assertThat(findElement(content, "$.name", String.class)).isEqualTo("name1");
		assertThat(findElement(content, "$.price", Integer.class)).isEqualTo(500);
		assertThat(findElement(content, "$.stockQuantity", Integer.class)).isEqualTo(500);
	}

	@Test
	public void update_성공_링크테스트(){
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));

		ItemDto updateDto = ItemDto.builder()
				.name("name1")
				.price(500)
				.stockQuantity(500)
				.details(null)
				.build();

		EntityModel<FoundItemDto> entityModel = itemRestController.update(saved.getId(), updateDto).getBody();
		assertThat(entityModel.getLink("all").get().toUri().getPath()).isEqualTo("/items");
		assertThat(entityModel.getLink("self").get().toUri().getPath()).isEqualTo("/items/"+saved.getId());
		assertThat(entityModel.getLink("update").get().toUri().getPath()).isEqualTo("/items/"+saved.getId());
		assertThat(entityModel.getLink("delete").get().toUri().getPath()).isEqualTo("/items/"+saved.getId());
	}

	@Test
	public void delete_성공_응답테스트() throws Exception {
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));

		String content = mockMvc.perform(delete("/items/"+saved.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		System.out.println(content);
		assertThat(findElement(content, "$.result", String.class)).isEqualTo("SUCCESS");
	}

	@Test
	public void delete_성공_링크테스트(){
		Item saved = itemRepository.saveAndFlush(createDummyItem(0));

		EntityModel<DeleteItemResponse> entityModel = itemRestController.deleteOne(saved.getId()).getBody();
		assertThat(entityModel.getLink("all").get().toUri().getPath()).isEqualTo("/items");
		assertThat(entityModel.getLink("self").get().toUri().getPath()).isEqualTo("/items/"+saved.getId());
	}

	private void saveAndFlushDummyItems(int size) {
		IntStream.range(0 , size).boxed()
				.map(i -> createDummyItem(i))
				.forEach(item -> itemRepository.save(item));
		itemRepository.flush();
	}
	/*
	name, tag, content 마지막에 lastNum이 붙은 인스턴스 생성
	 */
	private Item createDummyItem(int lastAddNum) {
		Item item = new Item("name"+lastAddNum, 1000, 1000);
		item.addDetail(new ItemDetail("tag"+lastAddNum, "content"+lastAddNum));
		return item;
	}
}
