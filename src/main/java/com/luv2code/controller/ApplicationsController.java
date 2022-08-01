

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
@RequestMapping("/Applications")
@Slf4j
@Validated
@Tag(name = "Applications_Controller")
public class ApplicationsController {

 @Autowired
 private ProjectService<Project> projectService;

 @Autowired
 private ApplicationsService<Applications> applicationsService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Applications> findApplicationsById(Integer id) {
		Applications applications = applicationsService.findById(id).get();
		return ResponseHandler.getResponse(applications);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Applications>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Applications> applications = applicationsService.findWithPaging(page, size);
		return ResponseHandler.getResponse(applications);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteApplications(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = applicationsService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Applications>> createApplications(@RequestBody @Valid List<ApplicationsVo.SaveApplications> applicationsVos) {
		List<Applications> applicationss = applicationsVos.stream().map( applicationsVo -> {
			Applications applications = CloneUtils.getClone(new Applications(), applicationsVo);
      			setFks(applications);
      			return applications;
		}).collect(Collectors.toList());
		List<Applications> response = applicationsService.createMany(applicationss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Applications>> updateApplications(@RequestBody List<ApplicationsVo.UpdateApplications> applicationsVos) {
		List<Applications> applicationss = applicationsVos.stream().map( applicationsVo -> {
			Applications foundApplications = applicationsService.findById(applicationsVo.getApplicationsId()).get();
			Applications applications = CloneUtils.getClone(foundApplications, applicationsVo);
      			setFks(applications);
      			return applications;
		}).collect(Collectors.toList());

		List<Applications> response = applicationsService.updateMany(applicationss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Applications>> filterApplications(@RequestBody ApplicationsVo.PublicApplications applicationsVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Applications applications = ObjectMapperUtils.getObjectMapper().convertValue(applicationsVo, new TypeReference<Applications>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(applications);
		log.info("queryMap: {}", queryMap);
		List<Applications> applicationsList = applicationsService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(applicationsList);
	}

 private Applications setFks(Applications applications) {

 CommonUtils.optionalChaining(()-> applications.getProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> applications.setProject(_project.get()));

 return applications;

 }

}
