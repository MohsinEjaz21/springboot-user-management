package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.ControlShortCutKey;

@SuppressWarnings("serial")
public class ControlShortCutKeyVo {

    public static class PublicControlShortCutKey extends ControlShortCutKey {}

   	@JsonIgnoreProperties("controlShortCutKeyId")
    public static class SaveControlShortCutKey extends ControlShortCutKey {}

    public static class UpdateControlShortCutKey extends ControlShortCutKey {}

}
