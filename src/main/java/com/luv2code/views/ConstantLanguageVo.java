package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.ConstantLanguage;

@SuppressWarnings("serial")
public class ConstantLanguageVo {

    public static class PublicConstantLanguage extends ConstantLanguage {}

   	@JsonIgnoreProperties("constantLanguageId")
    public static class SaveConstantLanguage extends ConstantLanguage {}

    public static class UpdateConstantLanguage extends ConstantLanguage {}

}
