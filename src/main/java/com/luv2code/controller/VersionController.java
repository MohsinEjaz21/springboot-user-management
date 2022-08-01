

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
@RequestMapping("/Version")
@Slf4j
@Validated
@Tag(name = "Version_Controller")
public class VersionController {

 @Autowired
 private UsersService<Users> usersService;
 @Autowired
 private ProjectService<Project> projectService;

 @Autowired
 private VersionService<Version> versionService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Version> findVersionById(Integer id) {
		Version version = versionService.findById(id).get();
		return ResponseHandler.getResponse(version);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Version>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Version> version = versionService.findWithPaging(page, size);
		return ResponseHandler.getResponse(version);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteVersion(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = versionService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Version>> createVersion(@RequestBody @Valid List<VersionVo.SaveVersion> versionVos) {
		List<Version> versions = versionVos.stream().map( versionVo -> {
			Version version = CloneUtils.getClone(new Version(), versionVo);
      			setFks(version);
      			return version;
		}).collect(Collectors.toList());
		List<Version> response = versionService.createMany(versions);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Version>> updateVersion(@RequestBody List<VersionVo.UpdateVersion> versionVos) {
		List<Version> versions = versionVos.stream().map( versionVo -> {
			Version foundVersion = versionService.findById(versionVo.getVersionId()).get();
			Version version = CloneUtils.getClone(foundVersion, versionVo);
      			setFks(version);
      			return version;
		}).collect(Collectors.toList());

		List<Version> response = versionService.updateMany(versions);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Version>> filterVersion(@RequestBody VersionVo.PublicVersion versionVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Version version = ObjectMapperUtils.getObjectMapper().convertValue(versionVo, new TypeReference<Version>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(version);
		log.info("queryMap: {}", queryMap);
		List<Version> versionList = versionService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(versionList);
	}

 private Version setFks(Version version) {

 CommonUtils.optionalChaining(()-> version.getUser().getUsersId())
 .map(_usersId -> usersService.findById(_usersId))
 .ifPresent(_users -> version.setUser(_users.get()));

 CommonUtils.optionalChaining(()-> version.getProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> version.setProject(_project.get()));

 return version;

 }

}
