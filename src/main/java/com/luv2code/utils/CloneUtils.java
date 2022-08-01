package com.luv2code.utils;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class CloneUtils {

	Map<String, String> fieldsHashMap = null;

	public <T> T getClone(T foundObj, T newObj) {
		List<Field> fields = ReflectionUtils.getFields(foundObj);

		for (Field field : fields) {
			field.setAccessible(true);
			try {

				if (isJumpToNextField(field, newObj)) {
					continue;
				}

				setFieldValue(field, foundObj, newObj);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return foundObj;
	}

	public <T> boolean isJumpToNextField(Field field, T obj) throws IllegalArgumentException, IllegalAccessException {
		boolean isJumpToNextField = false;
		List<Boolean> conditionList = new ArrayList<>();

		// log.info("field Type: {}", field.getType());
		conditionList.add(field.get(obj) == null);
		if (field.get(obj) != null) {
			conditionList.add(field.get(obj) instanceof Collection && ((Collection<?>) field.get(obj)).isEmpty());
			conditionList.add(field.get(obj) instanceof Map && ((Map<?, ?>) field.get(obj)).isEmpty());
			conditionList.add(field.get(obj) instanceof String && ((String) field.get(obj)).isEmpty());
			conditionList.add(field.get(obj) instanceof Date && ((Date) field.get(obj)).toString().isEmpty());
			conditionList.add(field.get(obj) instanceof Time && ((Time) field.get(obj)).toString().isEmpty());
			conditionList.add(field.get(obj) instanceof Timestamp && ((Timestamp) field.get(obj)).toString().isEmpty());
		}

		conditionList.add(field.getName().equals("serialVersionUID"));

		for (Boolean condition : conditionList) {
			if (condition) {
				isJumpToNextField = true;
				break;
			}
		}
		return isJumpToNextField;
	}


	private <T> void setFieldValue(Field field, T foundObj, T newObj)
	    throws IllegalArgumentException, IllegalAccessException {
		Class<?> fieldType = field.getType();

		if (fieldType.equals(java.util.Date.class)) {
			String dateStr = (String) field.get(newObj);
			java.sql.Date result = DateUtils.getInstance().getSqlDate(dateStr);
			if (result != null) {
				field.set(foundObj, result);
			}
		}

		else if (fieldType.equals(java.sql.Timestamp.class)) {
			Timestamp result = (Timestamp) field.get(newObj);
			if (result != null) {
				field.set(foundObj, result);
			}
		}

		else if (fieldType.equals(LocalTime.class)) {
			LocalTime result = (LocalTime) field.get(newObj);
			if (result != null) {
				field.set(foundObj, result);
			}
		}

		else if (fieldType.equals(java.util.List.class)) {
			List<?> list = (List<?>) field.get(newObj);
			if (list != null && !list.isEmpty()) {
				field.set(foundObj, list);
			}
		}

		else if (fieldType.equals(java.util.Set.class)) {
			Set<?> set = (Set<?>) field.get(newObj);
			if (set != null && !set.isEmpty()) {
				field.set(foundObj, set);
			}
		}

		else {
			field.set(foundObj, field.get(newObj));
		}

	}


}

//	private <T> void isDataTypeMatch(Field field, T obj) throws IllegalArgumentException, IllegalAccessException {
//String exceptionMsg = "";
//log.info("field Type: {}", field.getType() == field.get(obj).getClass());
//
//if (field.getType() != field.get(obj).getClass()) {
//exceptionMsg = "Data type mismatch for field: " + field.getName();
//log.error(exceptionMsg);
//throw new IllegalArgumentException(exceptionMsg);
//}
//}

//private <T> void validateModel(T t) {
//try {
//ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//Validator validator = factory.getValidator();
//Set<ConstraintViolation<T>> violations = validator.validate(t);
//if (violations.size() > 0) {
//	violations.forEach(violation -> {
//		HelperFns.prettyPrint(violation.getClass().getSimpleName());
//	});
//	throw new Exception(violations.toString());
//}
//} catch (Exception e) {
//e.printStackTrace();
//}
//}



//https://gist.github.com/isopropylcyanide/833e435cb261827e2d5af772ce8264a4

// if (field.getAnnotation(javax.persistence.ManyToOne.class) != null ||
//  field.getAnnotation(javax.persistence.OneToOne.class) != null) {
// } else {
// field.set(foundObj, field.get(newObj));
// }

// javax constraints validation for class
// public <T> String isValid(T t) {
// 	List<Field> fields = getFields(t);
// 	String exception="";
// 	for (Field field : fields) {
// 		field.setAccessible(true);
// 		try {
// 			if (field.isAnnotationPresent(javax.validation.constraints.Size.class)) {
// 				javax.validation.constraints.Size size = field
// 						.getAnnotation(javax.validation.constraints.Size.class);
// 				if (field.get(t) != null) {
// 					if (field.get(t).toString().length() > size.max()) {
// 						log.info("checking anotation");
// 						exception = field.get(t) + " Length is greater than maximum of size "+size.max();
// 					}
// 					if (field.get(t).toString().length() < size.min()) {
// 						exception  = field.get(t) + " Length is less than minimum of size "+size.min();
// 					}
// 				}
// 			}
// 		} catch (Exception e) {
// 			log.error("Error while validating user: {}", e.getMessage());
// 		}
// 	}
// 	return exception;
// }

// Class<?> clazz = newObj.getClass();
// Field[] fields = clazz.getDeclaredFields();

// log.info("new field : {} {} ", field.getName(), field.get(newObj));
