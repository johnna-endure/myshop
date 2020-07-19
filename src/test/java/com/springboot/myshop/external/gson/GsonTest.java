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
}
