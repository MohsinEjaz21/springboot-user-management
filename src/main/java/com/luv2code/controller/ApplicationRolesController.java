

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
@RequestMapping("/ApplicationRoles")
@Slf4j
@Validated
@Tag(name = "ApplicationRoles_Controller")
public class ApplicationRolesController {

 @Autowired
 private ProjectService<Project> projectService;
 @Autowired
 private ApplicationsService<Applications> applicationsService;

 @Autowired
 private ApplicationRolesService<ApplicationRoles> applicationRolesService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<ApplicationRoles> findApplicationRolesById(Integer id) {
		ApplicationRoles applicationRoles = applicationRolesService.findById(id).get();
		return ResponseHandler.getResponse(applicationRoles);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<ApplicationRoles>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<ApplicationRoles> applicationRoles = applicationRolesService.findWithPaging(page, size);
		return ResponseHandler.getResponse(applicationRoles);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteApplicationRoles(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = applicationRolesService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<ApplicationRoles>> createApplicationRoles(@RequestBody @Valid List<ApplicationRolesVo.SaveApplicationRoles> applicationRolesVos) {
		List<ApplicationRoles> applicationRoless = applicationRolesVos.stream().map( applicationRolesVo -> {
			ApplicationRoles applicationRoles = CloneUtils.getClone(new ApplicationRoles(), applicationRolesVo);
      			setFks(applicationRoles);
      			return applicationRoles;
		}).collect(Collectors.toList());
		List<ApplicationRoles> response = applicationRolesService.createMany(applicationRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<ApplicationRoles>> updateApplicationRoles(@RequestBody List<ApplicationRolesVo.UpdateApplicationRoles> applicationRolesVos) {
		List<ApplicationRoles> applicationRoless = applicationRolesVos.stream().map( applicationRolesVo -> {
			ApplicationRoles foundApplicationRoles = applicationRolesService.findById(applicationRolesVo.getApplicationRolesId()).get();
			ApplicationRoles applicationRoles = CloneUtils.getClone(foundApplicationRoles, applicationRolesVo);
      			setFks(applicationRoles);
      			return applicationRoles;
		}).collect(Collectors.toList());

		List<ApplicationRoles> response = applicationRolesService.updateMany(applicationRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<ApplicationRoles>> filterApplicationRoles(@RequestBody ApplicationRolesVo.PublicApplicationRoles applicationRolesVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		ApplicationRoles applicationRoles = ObjectMapperUtils.getObjectMapper().convertValue(applicationRolesVo, new TypeReference<ApplicationRoles>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(applicationRoles);
		log.info("queryMap: {}", queryMap);
		List<ApplicationRoles> applicationRolesList = applicationRolesService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(applicationRolesList);
	}

 private ApplicationRoles setFks(ApplicationRoles applicationRoles) {

 CommonUtils.optionalChaining(()-> applicationRoles.getProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> applicationRoles.setProject(_project.get()));

 CommonUtils.optionalChaining(()-> applicationRoles.getApplication().getApplicationsId())
 .map(_applicationsId -> applicationsService.findById(_applicationsId))
 .ifPresent(_applications -> applicationRoles.setApplication(_applications.get()));

 return applicationRoles;

 }

}
