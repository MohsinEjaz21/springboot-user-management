package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Users;

@SuppressWarnings("serial")
public class UsersVo {

    public static class PublicUsers extends Users {}

   	@JsonIgnoreProperties("usersId")
    public static class SaveUsers extends Users {}

    public static class UpdateUsers extends Users {}

}
