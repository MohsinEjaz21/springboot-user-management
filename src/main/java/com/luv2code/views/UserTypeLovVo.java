package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.UserTypeLov;

@SuppressWarnings("serial")
public class UserTypeLovVo {

    public static class PublicUserTypeLov extends UserTypeLov {}

   	@JsonIgnoreProperties("userTypeLovId")
    public static class SaveUserTypeLov extends UserTypeLov {}

    public static class UpdateUserTypeLov extends UserTypeLov {}

}
