package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.UserPreferences;

@SuppressWarnings("serial")
public class UserPreferencesVo {

    public static class PublicUserPreferences extends UserPreferences {}

   	@JsonIgnoreProperties("userPreferencesId")
    public static class SaveUserPreferences extends UserPreferences {}

    public static class UpdateUserPreferences extends UserPreferences {}

}
