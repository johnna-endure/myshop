package com.springboot.myshop.domain.order.subdomain.item.web.assembler;

import com.springboot.myshop.domain.order.subdomain.item.web.assembler.ItemModelAssembler;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.response.FoundItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemModelAssemblerTest {

	@Autowired
	private ItemModelAssembler assembler;

	@Test
	public void toModelTest(){
		FoundItemDto foundItemDto = FoundItemDto.builder()
				.name("name")
				.price(1000)
				.stockQuantity(1000)
				.id(1l)
				.details(new ArrayList<>())
				.build();
		EntityModel<FoundItemDto> entityModel = assembler.toModel(foundItemDto);

		assertThat(entityModel.getLink("all").get().toUri().getPath()).isEqualTo("/items");
		assertThat(entityModel.getLink("update").get().toUri().getPath()).isEqualTo("/items/"+foundItemDto.getId());
		assertThat(entityModel.getLink("delete").get().toUri().getPath()).isEqualTo("/items/"+foundItemDto.getId());
		assertThat(entityModel.getLink("self").get().toUri().getPath()).isEqualTo("/items/"+foundItemDto.getId());

	}
}
