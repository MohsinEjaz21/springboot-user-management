

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
@RequestMapping("/ProjectType")
@Slf4j
@Validated
@Tag(name = "ProjectType_Controller")
public class ProjectTypeController {

 @Autowired
 private ProjectTypeService<ProjectType> projectTypeService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<ProjectType> findProjectTypeById(Integer id) {
		ProjectType projectType = projectTypeService.findById(id).get();
		return ResponseHandler.getResponse(projectType);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<ProjectType>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<ProjectType> projectType = projectTypeService.findWithPaging(page, size);
		return ResponseHandler.getResponse(projectType);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteProjectType(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = projectTypeService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<ProjectType>> createProjectType(@RequestBody @Valid List<ProjectTypeVo.SaveProjectType> projectTypeVos) {
		List<ProjectType> projectTypes = projectTypeVos.stream().map( projectTypeVo -> {
			ProjectType projectType = CloneUtils.getClone(new ProjectType(), projectTypeVo);
      			return projectType;
		}).collect(Collectors.toList());
		List<ProjectType> response = projectTypeService.createMany(projectTypes);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<ProjectType>> updateProjectType(@RequestBody List<ProjectTypeVo.UpdateProjectType> projectTypeVos) {
		List<ProjectType> projectTypes = projectTypeVos.stream().map( projectTypeVo -> {
			ProjectType foundProjectType = projectTypeService.findById(projectTypeVo.getProjectTypeId()).get();
			ProjectType projectType = CloneUtils.getClone(foundProjectType, projectTypeVo);
      			return projectType;
		}).collect(Collectors.toList());

		List<ProjectType> response = projectTypeService.updateMany(projectTypes);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<ProjectType>> filterProjectType(@RequestBody ProjectTypeVo.PublicProjectType projectTypeVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		ProjectType projectType = ObjectMapperUtils.getObjectMapper().convertValue(projectTypeVo, new TypeReference<ProjectType>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(projectType);
		log.info("queryMap: {}", queryMap);
		List<ProjectType> projectTypeList = projectTypeService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(projectTypeList);
	}

}
