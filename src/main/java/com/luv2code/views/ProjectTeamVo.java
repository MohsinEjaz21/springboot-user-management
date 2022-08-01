package com.luv2code.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.entity.ProjectTeam;

@SuppressWarnings("serial")
public class ProjectTeamVo {

    public static class PublicProjectTeam extends ProjectTeam {}

   	@JsonIgnoreProperties("projectTeamId")
    public static class SaveProjectTeam extends ProjectTeam {}

    public static class UpdateProjectTeam extends ProjectTeam {}

}
