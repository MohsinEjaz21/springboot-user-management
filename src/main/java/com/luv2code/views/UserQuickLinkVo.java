package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.UserQuickLink;

@SuppressWarnings("serial")
public class UserQuickLinkVo {

    public static class PublicUserQuickLink extends UserQuickLink {}

   	@JsonIgnoreProperties("userQuickLinkId")
    public static class SaveUserQuickLink extends UserQuickLink {}

    public static class UpdateUserQuickLink extends UserQuickLink {}

}
