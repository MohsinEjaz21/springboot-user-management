package com.luv2code.utils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryUtils {

	private static final QueryUtils INSTANCE = new QueryUtils();

	public static QueryUtils getInstance() {
		return INSTANCE;
	}

	public <T> HashMap<String, Object> getQueryMap(T object) {
		return getQueryMap(object, "");
	}

	public <T> HashMap<String, Object> getQueryMap(T object, String excludeParameters) {
		HashMap<String, Object> response = new HashMap<>();
		StringBuilder query = new StringBuilder();
		HashMap<String, Object> queryParams = new HashMap<>();
		log.info(object.getClass().getSimpleName());

		try {
			query.append("SELECT u FROM ");
			query.append(object.getClass().getSimpleName());
			query.append(" u WHERE 1=1 ");
			Field[] fields = object.getClass().getDeclaredFields();

			// filterOut null, empty values and synthetic fields
			int i = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				boolean isJumpToNextField = CloneUtils.isJumpToNextField(field, object);
				
				if (isJumpToNextField) {
					continue;
				}

				if (isExcludeParameters(field, excludeParameters)) {
					continue;
				}

				if (field.isAnnotationPresent(JsonIgnore.class)) {
					continue;
				}

				setFieldValue(field, object, query, queryParams, i);

				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.put(Constants.QUERY, query.toString());
		response.put(Constants.QUERY_PARAMS, queryParams);

		return response;
	}

	boolean isExcludeParameters(Field field, String excludeParameters)
	    throws IllegalArgumentException, IllegalAccessException {
		boolean isJumpToNextField = false;
		if (!excludeParameters.equalsIgnoreCase("")) {
			String[] excludeParametersArray = excludeParameters.split(",");
			for (String excludeParameter : excludeParametersArray) {
				if (field.getName().equals(excludeParameter)) {
					isJumpToNextField = true;
					break;
				}
			}
		}
		return isJumpToNextField;

	}

	String wrapWith(String query, String wrapWith) {
		return wrapWith + query + wrapWith;
	}

	private <T> void setFieldValue(Field field, T obj, StringBuilder query, HashMap<String, Object> queryParams,
	    int index) throws IllegalArgumentException, IllegalAccessException {
		Class<?> fieldType = field.getType();

		String key = field.getName() + "_" + index;


		if (fieldType.equals(java.util.Date.class)) {
			String dateStr = (String) field.get(obj);
			java.sql.Date result = DateUtils.getInstance().getSqlDate(dateStr);
			if (result != null) {
				// if name includes start
				query.append(" AND ");
				if (field.getName().contains("start")) {
					query.append("u." + field.getName() + " >= :" + key);
				} else if (field.getName().contains("end")) {
					query.append("u." + field.getName() + " <= :" + key);
				} else {
					query.append("u." + field.getName() + " = :" + key);
				}
				queryParams.put(key, result);
			}
		}

		else if (fieldType.equals(java.sql.Timestamp.class)) {
			Timestamp result = (Timestamp) field.get(obj);
			log.info(result.toLocaleString());
			if (result != null) {
				 query.append(" AND ");
					if (field.getName().contains("start")) {
						query.append("u." + field.getName() + " >= :" + key);
					} else if (field.getName().contains("end")) {
						query.append("u." + field.getName() + " <= :" + key);
					} else {
						query.append("u." + field.getName() + " = :" + key);
					}
					queryParams.put(key, result);
			}
		}

		else if (fieldType.equals(LocalTime.class)) {
			LocalTime result = (LocalTime) field.get(obj);
			if (result != null) {
				query.append(" AND ");
				if (field.getName().contains("start")) {
					query.append("u." + field.getName() + " >= :" + key);
				} else if (field.getName().contains("end")) {
					query.append("u." + field.getName() + " <= :" + key);
				} else {
					query.append("u." + field.getName() + " = :" + key);
				}
				queryParams.put(key, result);
			}
		}


		else if (fieldType.equals(String.class)) {
			String value = (String) field.get(obj);
			if (value != null && !value.equals("")) {
				query.append(" AND ");
				query.append("Lower(u." + field.getName() + ")");
				query.append(" like Lower(:" + key + ")");
				queryParams.put(key, value);
			}
		}

		else if (fieldType.equals(java.util.List.class)) {
			List<?> list = (List<?>) field.get(obj);
			if (list != null && !list.isEmpty()) {
				query.append(" AND ");
				query.append("u." + field.getName());
				query.append(" = :" + key);
				queryParams.put(key, list);
			}
		}

		else if (fieldType.equals(java.util.Set.class)) {
			Set<?> set = (Set<?>) field.get(obj);
			if (set != null && !set.isEmpty()) {
				query.append(" AND ");
				query.append("u." + field.getName());
				query.append(" = :" + key);
				queryParams.put(key, set);
			}
		}

		else {
			query.append(" AND ");
			query.append("u." + field.getName());
			query.append(" = :" + key);
			queryParams.put(key, field.get(obj));
		}

	}

}



// create JPA Query From Object dynamically
