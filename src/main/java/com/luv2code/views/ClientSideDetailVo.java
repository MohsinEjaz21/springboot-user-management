package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.ClientSideDetail;

@SuppressWarnings("serial")
public class ClientSideDetailVo {

    public static class PublicClientSideDetail extends ClientSideDetail {}

   	@JsonIgnoreProperties("clientSideDetailId")
    public static class SaveClientSideDetail extends ClientSideDetail {}

    public static class UpdateClientSideDetail extends ClientSideDetail {}

}
