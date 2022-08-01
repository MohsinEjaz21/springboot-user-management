package com.luv2code.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class ReflectionUtils {

// make singleton instance
	Map<String, String> fieldsHashMap = null;
	

	protected <T> List<Field> getFields(T t) {
		List<Field> fields = new ArrayList<>();
		Class<?> clazz = t.getClass();
		while (clazz != Object.class) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fields;
	}

	public <T> Object getNestedValue(Object object, String key) {
		String[] keys = key.split("\\.");
		for (String k : keys) {
			if (object == null) {
				return null;
			}
			object = getValue(object, k);
		}
		return object;
	}
	private <T> Object getValue(Object object, String key) {
		if (object instanceof Map) {
			return ((Map<String, Object>) object).get(key);
		} else if (object instanceof List) {
			return ((List<Object>) object).get(Integer.parseInt(key));
		} else {
			return getFieldValue(object, key);
		}
	}
	private <T> Object getFieldValue(Object object, String key) {
		Field field = getField(object, key);
		if (field == null) {
			return null;
		}
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}
	private <T> Field getField(Object object, String key) {
		Class<?> clazz = object.getClass();
		while (clazz != Object.class) {
			try {
				return clazz.getDeclaredField(key);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}
		return null;
	}

}
