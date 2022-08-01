package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.LabelConstants;

@SuppressWarnings("serial")
public class LabelConstantsVo {

    public static class PublicLabelConstants extends LabelConstants {}

   	@JsonIgnoreProperties("labelConstantsId")
    public static class SaveLabelConstants extends LabelConstants {}

    public static class UpdateLabelConstants extends LabelConstants {}

}
