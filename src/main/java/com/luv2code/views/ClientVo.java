package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Client;

@SuppressWarnings("serial")
public class ClientVo {

    public static class PublicClient extends Client {}

   	@JsonIgnoreProperties("clientId")
    public static class SaveClient extends Client {}

    public static class UpdateClient extends Client {}

}
