package com.springboot.myshop.domain.order.subdomain.item.web.controller;

import com.springboot.myshop.common.enums.DeleteResult;
import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.web.assembler.ItemModelAssembler;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response.DeleteItemResponse;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response.FoundItemDto;
import com.springboot.myshop.domain.order.subdomain.item.web.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/items", produces = "application/json")
public class ItemRestController {

	private final ItemService itemService;
	private final ItemModelAssembler assembler;

	@GetMapping
	public CollectionModel<EntityModel<FoundItemDto>> all(@RequestParam(required = false) Integer page,
	                                                      @RequestParam(required = false) Integer size) {
		page = Objects.isNull(page) ? 0 : page;
		size = Objects.isNull(size) ? 5 : size;
		//findAll 리팩토링 필요. 내부조인으로 구현
		List<EntityModel<FoundItemDto>> items = itemService.findAll(page, size).stream()
				.map(item -> FoundItemDto.of(item))
				.map(foundDto -> assembler.toModel(foundDto))
				.collect(Collectors.toList());
		return CollectionModel.of(items,
				linkTo(methodOn(ItemRestController.class).all(page, size)).withSelfRel(),
				linkTo(methodOn(ItemRestController.class).create(null)).withRel("create"));
	}

	@GetMapping("/{id}")
	public EntityModel<FoundItemDto> one(@PathVariable Long id) {
		return assembler.toModel(FoundItemDto.of(itemService.findOne(id)));
	}

	@PostMapping
	public ResponseEntity<EntityModel<FoundItemDto>> create(@RequestBody ItemDto createDto) {
		Item savedItem = itemService.create(createDto);
		FoundItemDto foundItemDto = FoundItemDto.of(savedItem);

		return ResponseEntity.created(linkTo(methodOn(ItemRestController.class).one(savedItem.getId())).toUri())
				.body(assembler.toModel(foundItemDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<FoundItemDto>> update(@PathVariable Long id,
	                                                        @RequestBody ItemDto updateDto) {
		FoundItemDto foundItemDto = FoundItemDto.of(itemService.update(id, updateDto));
		return ResponseEntity
				.created(linkTo(methodOn(ItemRestController.class).one(id)).toUri())
				.body(assembler.toModel(foundItemDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<EntityModel<DeleteItemResponse>> deleteOne(@PathVariable Long id) {
		itemService.delete(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(EntityModel.of(new DeleteItemResponse(DeleteResult.SUCCESS),
						linkTo(methodOn(ItemRestController.class).all(null, null)).withRel("all"),
						linkTo(methodOn(ItemRestController.class).deleteOne(id)).withSelfRel()));
	}
}
