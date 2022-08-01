

package com.luv2code.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.luv2code.entity.*;
import com.luv2code.response.*;
import com.luv2code.service.*;
import com.luv2code.utils.*;
import com.luv2code.views.*;

import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ProjectTeam")
@Slf4j
@Validated
@Tag(name = "ProjectTeam_Controller")
public class ProjectTeamController {

 @Autowired
 private ProjectService<Project> projectService;
 @Autowired
 private BusinessRolesService<BusinessRoles> businessRolesService;
 @Autowired
 private DepartmentService<Department> departmentService;
 @Autowired
 private DepartmentRolesService<DepartmentRoles> departmentRolesService;
 @Autowired
 private UserTypeLovService<UserTypeLov> userTypeLovService;

 @Autowired
 private ProjectTeamService<ProjectTeam> projectTeamService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<ProjectTeam> findProjectTeamById(Integer id) {
		ProjectTeam projectTeam = projectTeamService.findById(id).get();
		return ResponseHandler.getResponse(projectTeam);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<ProjectTeam>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<ProjectTeam> projectTeam = projectTeamService.findWithPaging(page, size);
		return ResponseHandler.getResponse(projectTeam);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteProjectTeam(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = projectTeamService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<ProjectTeam>> createProjectTeam(@RequestBody @Valid List<ProjectTeamVo.SaveProjectTeam> projectTeamVos) {
		List<ProjectTeam> projectTeams = projectTeamVos.stream().map( projectTeamVo -> {
			ProjectTeam projectTeam = CloneUtils.getClone(new ProjectTeam(), projectTeamVo);
      			setFks(projectTeam);
      			return projectTeam;
		}).collect(Collectors.toList());
		List<ProjectTeam> response = projectTeamService.createMany(projectTeams);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<ProjectTeam>> updateProjectTeam(@RequestBody List<ProjectTeamVo.UpdateProjectTeam> projectTeamVos) {
		List<ProjectTeam> projectTeams = projectTeamVos.stream().map( projectTeamVo -> {
			ProjectTeam foundProjectTeam = projectTeamService.findById(projectTeamVo.getProjectTeamId()).get();
			ProjectTeam projectTeam = CloneUtils.getClone(foundProjectTeam, projectTeamVo);
      			setFks(projectTeam);
      			return projectTeam;
		}).collect(Collectors.toList());

		List<ProjectTeam> response = projectTeamService.updateMany(projectTeams);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<ProjectTeam>> filterProjectTeam(@RequestBody ProjectTeamVo.PublicProjectTeam projectTeamVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		ProjectTeam projectTeam = ObjectMapperUtils.getObjectMapper().convertValue(projectTeamVo, new TypeReference<ProjectTeam>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(projectTeam);
		log.info("queryMap: {}", queryMap);
		List<ProjectTeam> projectTeamList = projectTeamService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(projectTeamList);
	}

 private ProjectTeam setFks(ProjectTeam projectTeam) {

 CommonUtils.optionalChaining(()-> projectTeam.getProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> projectTeam.setProject(_project.get()));

 CommonUtils.optionalChaining(()-> projectTeam.getBusinessRole().getBusinessRolesId())
 .map(_businessRolesId -> businessRolesService.findById(_businessRolesId))
 .ifPresent(_businessRoles -> projectTeam.setBusinessRole(_businessRoles.get()));

 CommonUtils.optionalChaining(()-> projectTeam.getDepartment().getDepartmentId())
 .map(_departmentId -> departmentService.findById(_departmentId))
 .ifPresent(_department -> projectTeam.setDepartment(_department.get()));

 CommonUtils.optionalChaining(()-> projectTeam.getDepartmentRole().getDepartmentRolesId())
 .map(_departmentRolesId -> departmentRolesService.findById(_departmentRolesId))
 .ifPresent(_departmentRoles -> projectTeam.setDepartmentRole(_departmentRoles.get()));

 CommonUtils.optionalChaining(()-> projectTeam.getUserTypeLov().getUserTypeLovId())
 .map(_userTypeLovId -> userTypeLovService.findById(_userTypeLovId))
 .ifPresent(_userTypeLov -> projectTeam.setUserTypeLov(_userTypeLov.get()));

 return projectTeam;

 }

}
