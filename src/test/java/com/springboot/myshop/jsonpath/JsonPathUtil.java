package com.springboot.myshop.jsonpath;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;

public class JsonPathUtil {

	public static <T> T findElement(String json, String exp, Class<T> type) {
		return (T)JsonPath.read(json, exp);
	}

	public static List<Object> findListElement(String json, String exp) {
		return (List<Object>) JsonPath.read(json, exp);
	}

	public static Map<Object, Object> findObjectElement(String json, String exp) {
		return (Map<Object, Object>) JsonPath.read(json, exp);
	}
}
