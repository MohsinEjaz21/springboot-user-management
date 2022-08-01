package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.UserDepartment;

@SuppressWarnings("serial")
public class UserDepartmentVo {

    public static class PublicUserDepartment extends UserDepartment {}

   	@JsonIgnoreProperties("userDepartmentId")
    public static class SaveUserDepartment extends UserDepartment {}

    public static class UpdateUserDepartment extends UserDepartment {}

}
