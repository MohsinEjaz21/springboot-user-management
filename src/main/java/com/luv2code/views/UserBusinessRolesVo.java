package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.UserBusinessRoles;

@SuppressWarnings("serial")
public class UserBusinessRolesVo {

    public static class PublicUserBusinessRoles extends UserBusinessRoles {}

   	@JsonIgnoreProperties("userBusinessRolesId")
    public static class SaveUserBusinessRoles extends UserBusinessRoles {}

    public static class UpdateUserBusinessRoles extends UserBusinessRoles {}

}
