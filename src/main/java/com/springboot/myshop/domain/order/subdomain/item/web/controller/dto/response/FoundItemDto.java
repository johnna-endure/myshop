package com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response;

import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDetailDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ToString
@NoArgsConstructor
@Getter
@Relation(itemRelation = "item", collectionRelation = "items")
public class FoundItemDto {

	private Long id;
	private String name;
	private Integer price;
	private Integer stockQuantity;

	private List<FoundItemDetailDto> details;
	@Builder
	public FoundItemDto(Long id, String name, Integer price, Integer stockQuantity, List<FoundItemDetailDto> details) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.details = details;
	}

	public static FoundItemDto of(Item foundItem) {
		List<FoundItemDetailDto> details = foundItem.getDetails().stream()
				.map(detail -> FoundItemDetailDto.of(detail))
				.collect(toList());

		return FoundItemDto.builder()
				.id(foundItem.getId())
				.name(foundItem.getName())
				.price(foundItem.getPrice())
				.stockQuantity(foundItem.getStockQuantity())
				.details(details).build();
	}

}
