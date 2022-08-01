package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Department;

@SuppressWarnings("serial")
public class DepartmentVo {

    public static class PublicDepartment extends Department {}

   	@JsonIgnoreProperties("departmentId")
    public static class SaveDepartment extends Department {}

    public static class UpdateDepartment extends Department {}

}
