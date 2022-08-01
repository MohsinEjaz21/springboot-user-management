package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.FormLabelConstantTranslation;

@SuppressWarnings("serial")
public class FormLabelConstantTranslationVo {

    public static class PublicFormLabelConstantTranslation extends FormLabelConstantTranslation {}

   	@JsonIgnoreProperties("formLabelConstantTranslationId")
    public static class SaveFormLabelConstantTranslation extends FormLabelConstantTranslation {}

    public static class UpdateFormLabelConstantTranslation extends FormLabelConstantTranslation {}

}
