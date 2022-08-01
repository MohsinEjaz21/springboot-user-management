

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
@RequestMapping("/Project")
@Slf4j
@Validated
@Tag(name = "Project_Controller")
public class ProjectController {

 @Autowired
 private UsersService<Users> usersService;
 @Autowired
 private ClientService<Client> clientService;
 @Autowired
 private ProjectTypeService<ProjectType> projectTypeService;

 @Autowired
 private ProjectService<Project> projectService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Project> findProjectById(Integer id) {
		Project project = projectService.findById(id).get();
		return ResponseHandler.getResponse(project);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Project>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Project> project = projectService.findWithPaging(page, size);
		return ResponseHandler.getResponse(project);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteProject(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = projectService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Project>> createProject(@RequestBody @Valid List<ProjectVo.SaveProject> projectVos) {
		List<Project> projects = projectVos.stream().map( projectVo -> {
			Project project = CloneUtils.getClone(new Project(), projectVo);
      			setFks(project);
      			return project;
		}).collect(Collectors.toList());
		List<Project> response = projectService.createMany(projects);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Project>> updateProject(@RequestBody List<ProjectVo.UpdateProject> projectVos) {
		List<Project> projects = projectVos.stream().map( projectVo -> {
			Project foundProject = projectService.findById(projectVo.getProjectId()).get();
			Project project = CloneUtils.getClone(foundProject, projectVo);
      			setFks(project);
      			return project;
		}).collect(Collectors.toList());

		List<Project> response = projectService.updateMany(projects);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Project>> filterProject(@RequestBody ProjectVo.PublicProject projectVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Project project = ObjectMapperUtils.getObjectMapper().convertValue(projectVo, new TypeReference<Project>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(project);
		log.info("queryMap: {}", queryMap);
		List<Project> projectList = projectService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(projectList);
	}

 private Project setFks(Project project) {

 CommonUtils.optionalChaining(()-> project.getImportUser().getUsersId())
 .map(_usersId -> usersService.findById(_usersId))
 .ifPresent(_users -> project.setImportUser(_users.get()));

 CommonUtils.optionalChaining(()-> project.getClient().getClientId())
 .map(_clientId -> clientService.findById(_clientId))
 .ifPresent(_client -> project.setClient(_client.get()));

 CommonUtils.optionalChaining(()-> project.getProjectType().getProjectTypeId())
 .map(_projectTypeId -> projectTypeService.findById(_projectTypeId))
 .ifPresent(_projectType -> project.setProjectType(_projectType.get()));

 return project;

 }

}
