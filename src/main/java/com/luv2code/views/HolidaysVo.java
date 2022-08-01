package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Holidays;

@SuppressWarnings("serial")
public class HolidaysVo {

    public static class PublicHolidays extends Holidays {}

   	@JsonIgnoreProperties("holidaysId")
    public static class SaveHolidays extends Holidays {}

    public static class UpdateHolidays extends Holidays {}

}
