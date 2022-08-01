package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.Project;

@SuppressWarnings("serial")
public class ProjectVo {

    public static class PublicProject extends Project {}

   	@JsonIgnoreProperties("projectId")
    public static class SaveProject extends Project {}

    public static class UpdateProject extends Project {}

}
