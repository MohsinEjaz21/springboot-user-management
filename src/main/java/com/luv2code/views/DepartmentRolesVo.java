package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.DepartmentRoles;

@SuppressWarnings("serial")
public class DepartmentRolesVo {

    public static class PublicDepartmentRoles extends DepartmentRoles {}

   	@JsonIgnoreProperties("departmentRolesId")
    public static class SaveDepartmentRoles extends DepartmentRoles {}

    public static class UpdateDepartmentRoles extends DepartmentRoles {}

}
