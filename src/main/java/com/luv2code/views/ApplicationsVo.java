package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Applications;

@SuppressWarnings("serial")
public class ApplicationsVo {

    public static class PublicApplications extends Applications {}

   	@JsonIgnoreProperties("applicationsId")
    public static class SaveApplications extends Applications {}

    public static class UpdateApplications extends Applications {}

}
