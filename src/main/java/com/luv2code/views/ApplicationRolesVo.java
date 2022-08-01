package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.ApplicationRoles;

@SuppressWarnings("serial")
public class ApplicationRolesVo {

    public static class PublicApplicationRoles extends ApplicationRoles {}

   	@JsonIgnoreProperties("applicationRolesId")
    public static class SaveApplicationRoles extends ApplicationRoles {}

    public static class UpdateApplicationRoles extends ApplicationRoles {}

}
