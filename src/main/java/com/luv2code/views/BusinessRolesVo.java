package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.BusinessRoles;

@SuppressWarnings("serial")
public class BusinessRolesVo {

    public static class PublicBusinessRoles extends BusinessRoles {}

   	@JsonIgnoreProperties("businessRolesId")
    public static class SaveBusinessRoles extends BusinessRoles {}

    public static class UpdateBusinessRoles extends BusinessRoles {}

}
