package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.WorkingDays;

@SuppressWarnings("serial")
public class WorkingDaysVo {

    public static class PublicWorkingDays extends WorkingDays {}

   	@JsonIgnoreProperties("workingDaysId")
    public static class SaveWorkingDays extends WorkingDays {}

    public static class UpdateWorkingDays extends WorkingDays {}

}
