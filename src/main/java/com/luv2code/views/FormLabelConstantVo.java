package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.FormLabelConstant;

@SuppressWarnings("serial")
public class FormLabelConstantVo {

    public static class PublicFormLabelConstant extends FormLabelConstant {}

   	@JsonIgnoreProperties("formLabelConstantId")
    public static class SaveFormLabelConstant extends FormLabelConstant {}

    public static class UpdateFormLabelConstant extends FormLabelConstant {}

}
