package com.springboot.myshop.domain.order.subdomain.item.aspect;

import com.springboot.myshop.common.exception.ValidationException;
import com.springboot.myshop.domain.order.subdomain.item.web.controller.dto.ItemDto;
import com.springboot.myshop.domain.order.subdomain.item.web.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ItemValidationAspectTest {

	@Autowired
	private ItemService itemService;

	@Test
	public void validateBeforeCreate_예외_던지는지(){
		ItemDto itemDto = new ItemDto();
		assertThatThrownBy(() -> itemService.create(itemDto)).isExactlyInstanceOf(ValidationException.class);
	}

	@Test
	public void validateBeforeUpdate_예외_던지는지(){
		ItemDto itemDto = new ItemDto();
		assertThatThrownBy(() -> itemService.update(null,itemDto)).isExactlyInstanceOf(ValidationException.class);
	}

}
