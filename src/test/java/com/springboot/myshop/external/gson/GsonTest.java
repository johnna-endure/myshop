package com.springboot.myshop.external.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GsonTest {

	private Gson gson = new Gson();

	@Test
	public void makeJsonObject() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name", "cws");
		jsonObject.addProperty("msg","hello");

		String expected = "{\"name\":\"cws\",\"msg\":\"hello\"}";
		assertThat(jsonObject.toString()).isEqualTo(expected);
	}

	@Test
	public void mapConvertToJson(){
		Map<String, String> map = new HashMap<>();
		map.put("key1", "val1");
		map.put("key2", "val2");

		String json = gson.toJson(map);
		System.out.println(json);
	}
}
