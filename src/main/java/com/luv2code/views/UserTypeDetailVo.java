package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.UserTypeDetail;

@SuppressWarnings("serial")
public class UserTypeDetailVo {

    public static class PublicUserTypeDetail extends UserTypeDetail {}

   	@JsonIgnoreProperties("userTypeDetailId")
    public static class SaveUserTypeDetail extends UserTypeDetail {}

    public static class UpdateUserTypeDetail extends UserTypeDetail {}

}
