package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.ProjectType;

@SuppressWarnings("serial")
public class ProjectTypeVo {

    public static class PublicProjectType extends ProjectType {}

   	@JsonIgnoreProperties("projectTypeId")
    public static class SaveProjectType extends ProjectType {}

    public static class UpdateProjectType extends ProjectType {}

}
