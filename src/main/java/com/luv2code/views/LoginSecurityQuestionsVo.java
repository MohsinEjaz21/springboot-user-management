package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.LoginSecurityQuestions;

@SuppressWarnings("serial")
public class LoginSecurityQuestionsVo {

    public static class PublicLoginSecurityQuestions extends LoginSecurityQuestions {}

   	@JsonIgnoreProperties("loginSecurityQuestionsId")
    public static class SaveLoginSecurityQuestions extends LoginSecurityQuestions {}

    public static class UpdateLoginSecurityQuestions extends LoginSecurityQuestions {}

}
