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
public class LogUtils {

	public void prettyPrint(Object o) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(o);
		log.info("response : " + jsonOutput);
	}

	public String getStringFromObject(Object o) {
		Gson gson = new GsonBuilder().create();
		String jsonOutput = gson.toJson(o);
		return jsonOutput;
	}


}

