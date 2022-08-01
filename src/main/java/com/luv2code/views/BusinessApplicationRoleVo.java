package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.BusinessApplicationRole;

@SuppressWarnings("serial")
public class BusinessApplicationRoleVo {

    public static class PublicBusinessApplicationRole extends BusinessApplicationRole {}

   	@JsonIgnoreProperties("businessApplicationRoleId")
    public static class SaveBusinessApplicationRole extends BusinessApplicationRole {}

    public static class UpdateBusinessApplicationRole extends BusinessApplicationRole {}

}
