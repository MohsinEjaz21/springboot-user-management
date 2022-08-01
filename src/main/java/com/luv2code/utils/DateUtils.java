package com.luv2code.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

	enum DateFormate {
		YYYY_MM_DD("yyyy-MM-dd"), YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss");

		String value;

		DateFormate(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private DateUtils() {
	}

	private static DateUtils instance = new DateUtils();

	public static DateUtils getInstance() {
		return instance;
	}

	public LocalDate getDateFromString(String date) {
		String string = "January 2, 2010";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormate.YYYY_MM_DD.value, Locale.ENGLISH);
		LocalDate formatedDate = LocalDate.parse(string, formatter);
		return formatedDate;
	}

	public java.sql.Date getSqlDate(String date) {
		java.sql.Date sqlDate = null;
		try {
			java.util.Date utilDate = new SimpleDateFormat(DateFormate.YYYY_MM_DD.value).parse(date);
			sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			log.error("Error in Date String Parse : " + e.getMessage());
			e.printStackTrace();
		}
		return sqlDate;
	}

	public Timestamp getTimestampFromString(String timestampStr) {
		return Timestamp.valueOf(timestampStr);
	}

	public Date getTimeFromString(String time) {
		java.util.Date date = null;
		try {
			DateFormat sdf = new SimpleDateFormat("hh:mm");
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public LocalTime getSqlTimeFromString(String time) {
    return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

	}

}
