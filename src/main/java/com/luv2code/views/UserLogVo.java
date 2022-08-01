package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.UserLog;

@SuppressWarnings("serial")
public class UserLogVo {

    public static class PublicUserLog extends UserLog {}

   	@JsonIgnoreProperties("userLogId")
    public static class SaveUserLog extends UserLog {}

    public static class UpdateUserLog extends UserLog {}

}
