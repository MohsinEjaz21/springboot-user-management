package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Language;

@SuppressWarnings("serial")
public class LanguageVo {

    public static class PublicLanguage extends Language {}

   	@JsonIgnoreProperties("languageId")
    public static class SaveLanguage extends Language {}

    public static class UpdateLanguage extends Language {}

}
