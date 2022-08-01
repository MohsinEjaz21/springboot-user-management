package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Version;

@SuppressWarnings("serial")
public class VersionVo {

    public static class PublicVersion extends Version {}

   	@JsonIgnoreProperties("versionId")
    public static class SaveVersion extends Version {}

    public static class UpdateVersion extends Version {}

}
