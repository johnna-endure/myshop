package com.springboot.myshop.domain.customer.web.rest.controller;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.springboot.myshop.domain.customer.dto.CustomerCreateDto;
import com.springboot.myshop.domain.customer.dto.CustomerUpdateDto;
import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.exception.NotFoundCustomerException;
import com.springboot.myshop.domain.customer.repository.CustomerRepository;
import com.springboot.myshop.domain.customer.value.Address;
import com.springboot.myshop.domain.customer.web.rest.controller.assembler.CustomerModelAssembler;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerModelAssembler assembler;

	@Autowired
	private MockMvc mockMvc;
	private Gson gson = new Gson();

	@AfterEach
	public void afterEach() {
		customerRepository.deleteAll();
	}

	@Test
	public void all_파라미터_없고_사이즈보다_데이터_적은_경우() throws Exception {
		//given
		IntStream.range(0,3).boxed()
				.map(i -> new Customer("email"+i, "1234", new Address("address")))
				.forEach(customer -> customerRepository.save(customer));
		//when
		String content = mockMvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		//then
		//페이징 개수 체크
		List<String> customers = JsonPath.read(content,"$..customers.*");
		assertThat(customers.size()).isEqualTo(3);
		//email 필드를 통해 동등성 체크
		List<String> customerEmails = JsonPath.read(content, "$..email");
		assertThat(customerEmails).isEqualTo(Lists.list("email0","email1","email2"));
	}

	@Test
	public void all_페이징_page만_전달한_경우() throws Exception {
		//given
		IntStream.range(0,7).boxed()
				.map(i -> new Customer("email"+i, "1234", new Address("address")))
				.forEach(customer -> customerRepository.save(customer));
		//when1 : 첫번째 페이지 요청
		String firstPageContent = mockMvc.perform(
				get("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.param("page","0"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		//then1
		List<String> firstPage = JsonPath.read(firstPageContent, "$..customers.*");
		assertThat(firstPage.size()).isEqualTo(5);

		List<String> firstPageEmails = JsonPath.read(firstPageContent, "$..email");
		assertThat(firstPageEmails).isEqualTo(Lists.list("email0","email1","email2","email3","email4"));

		//when2 : 두번째 페이지
		String secondPageContent = mockMvc.perform(
				get("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.param("page","1"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		//then2
		List<String> secondPage = JsonPath.read(secondPageContent, "$..customers.*");
		assertThat(secondPage.size()).isEqualTo(2);

		List<String> secondPageEmails = JsonPath.read(secondPageContent, "$..email");
		assertThat(secondPageEmails).isEqualTo(Lists.list("email5","email6"));
	}

	@Test
	public void all_페이징_page와_size_전달한_경우() throws Exception {
		//given
		IntStream.range(0,7).boxed()
				.map(i -> new Customer("email"+i, "1234", new Address("address")))
				.forEach(customer -> customerRepository.save(customer));
		//when1 : 첫번째 페이지 요청
		String firstPageContent = mockMvc.perform(
				get("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.param("page","0")
						.param("size","4"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		//then1
		List<String> firstPage = JsonPath.read(firstPageContent, "$..customers.*");
		assertThat(firstPage.size()).isEqualTo(4);

		List<String> firstPageEmails = JsonPath.read(firstPage, "$..email");
		assertThat(firstPageEmails).isEqualTo(Lists.list("email0","email1","email2","email3"));

		//when2 : 두번째 페이지
		String secondPageContent = mockMvc.perform(
				get("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.param("page","1")
						.param("size", "4"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		//then2
		List<String> secondPage = JsonPath.read(secondPageContent, "$..customers.*");
		assertThat(secondPage.size()).isEqualTo(3);

		List<String> secondPageEmails = JsonPath.read(secondPageContent, "$..email");
		assertThat(secondPageEmails).isEqualTo(Lists.list("email4","email5", "email6"));
	}

	@Test
	public void one_조회_성공한_경우() throws Exception {
		//given
		Customer customer = Customer.builder()
				.email("email")
				.password("1234")
				.address(new Address("address")).build();
		Customer savedCustomer = customerRepository.save(customer);

		//when
		String content = mockMvc.perform(
				get("/customers/" + savedCustomer.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		//then
		assertThat((Integer) JsonPath.read(content, "$.id")).isEqualTo(savedCustomer.getId().intValue());
		assertThat(findJsonElement(content, "$.email")).isEqualTo("email");
	}

	@Test
	public void one_없는_id_조회한_경우() throws Exception {
		String content = mockMvc.perform(
				get("/customers/1")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();

		assertThat(findJsonElement(content,"$.url")).isEqualTo("/customers/1"); //mock 테스트라서 호스트뒤에 포트번호 생략
		assertThat(findJsonElement(content,"$.method")).isEqualTo("GET");
		assertThat(findJsonElement(content,"$.message")).isEqualTo(new NotFoundCustomerException(1l).getMessage());
	}

	@Test
	public void create_생성_성공한_경우() throws Exception {
		CustomerCreateDto creationDto = CustomerCreateDto.builder()
				.email("email")
				.password("password")
				.address(new Address("address"))
				.build();


		String content = mockMvc.perform(
				post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(creationDto)))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();

		assertThat(findJsonElement(content, "$.id")).isNotNull();
		assertThat(findJsonElement(content, "$.email")).isEqualTo("email");
		assertThat(findJsonElement(content, "$.address.address")).isEqualTo("address");
		assertThat(findJsonElement(content, "$.createdDatetime")).isNotNull();
		assertThat(findJsonElement(content, "$.modifiedDatetime")).isNotNull();
	}

	@Test @Disabled
	public void create_createDto_emailIsNull() throws Exception {
	}

	@Test @Disabled
	public void create_createDto_passwordIsNull() throws Exception {
		CustomerCreateDto dto = CustomerCreateDto.builder()
				.email("email")
				.password(null)
				.address(new Address("address"))
				.build();
		String content = mockMvc.perform(
				post("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(dto)))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();

		assertThat(findJsonElement(content, "$.url")).isEqualTo("/customers");
		assertThat(findJsonElement(content, "$.method")).isEqualToIgnoringCase("post");
		assertThat(findJsonElement(content, "$.message")).isEqualTo("password 값이 없습니다.");
	}


	@Test @Disabled
	public void create_createDto_addressIsNull() throws Exception {
		CustomerCreateDto dto = CustomerCreateDto.builder()
				.email("email")
				.password("1234")
				.address(null)
				.build();
		String content = mockMvc.perform(
				post("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(dto)))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();

		assertThat(findJsonElement(content, "$.url")).isEqualTo("/customers");
		assertThat(findJsonElement(content, "$.method")).isEqualToIgnoringCase("post");
		assertThat(findJsonElement(content, "$.message")).isEqualTo("address 값이 없습니다.");
	}

	@Test
	public void update_변경에_성공한_경우() throws Exception {
		//given
		Customer saved = customerRepository.save(
				new Customer("email", "1234", new Address("address")));

		//when
		CustomerUpdateDto updateDto = new CustomerUpdateDto("5678", new Address("address1"));
		mockMvc.perform(
				put("/customers/" + saved.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(updateDto)))
				.andExpect(status().isCreated());

	}

	@Test @Disabled
	public void update_변경에_실패한_경우(){
		
	}
	
	@Test
	public void delete_삭제_성공한_경우() throws Exception {
		//given
		Customer saved = customerRepository.save(
				new Customer("email", "1234", new Address("address")));

		String content = mockMvc.perform(
				delete("/customers/"+saved.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertThat(customerRepository.findById(saved.getId())).isEqualTo(Optional.empty());
		System.out.println(content);
	}

	@Test @Disabled
	public void delete_삭제_실패한_경우() {

	}
	
	
	public String findJsonElement(String json, String path) {
		return JsonPath.read(json, path).toString();
	}

}
