

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
@RequestMapping("/UserBusinessRoles")
@Slf4j
@Validated
@Tag(name = "UserBusinessRoles_Controller")
public class UserBusinessRolesController {

 @Autowired
 private BusinessRolesService<BusinessRoles> businessRolesService;
 @Autowired
 private ProjectService<Project> projectService;

 @Autowired
 private UserBusinessRolesService<UserBusinessRoles> userBusinessRolesService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<UserBusinessRoles> findUserBusinessRolesById(Integer id) {
		UserBusinessRoles userBusinessRoles = userBusinessRolesService.findById(id).get();
		return ResponseHandler.getResponse(userBusinessRoles);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<UserBusinessRoles>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<UserBusinessRoles> userBusinessRoles = userBusinessRolesService.findWithPaging(page, size);
		return ResponseHandler.getResponse(userBusinessRoles);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUserBusinessRoles(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = userBusinessRolesService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<UserBusinessRoles>> createUserBusinessRoles(@RequestBody @Valid List<UserBusinessRolesVo.SaveUserBusinessRoles> userBusinessRolesVos) {
		List<UserBusinessRoles> userBusinessRoless = userBusinessRolesVos.stream().map( userBusinessRolesVo -> {
			UserBusinessRoles userBusinessRoles = CloneUtils.getClone(new UserBusinessRoles(), userBusinessRolesVo);
      			setFks(userBusinessRoles);
      			return userBusinessRoles;
		}).collect(Collectors.toList());
		List<UserBusinessRoles> response = userBusinessRolesService.createMany(userBusinessRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<UserBusinessRoles>> updateUserBusinessRoles(@RequestBody List<UserBusinessRolesVo.UpdateUserBusinessRoles> userBusinessRolesVos) {
		List<UserBusinessRoles> userBusinessRoless = userBusinessRolesVos.stream().map( userBusinessRolesVo -> {
			UserBusinessRoles foundUserBusinessRoles = userBusinessRolesService.findById(userBusinessRolesVo.getUserBusinessRolesId()).get();
			UserBusinessRoles userBusinessRoles = CloneUtils.getClone(foundUserBusinessRoles, userBusinessRolesVo);
      			setFks(userBusinessRoles);
      			return userBusinessRoles;
		}).collect(Collectors.toList());

		List<UserBusinessRoles> response = userBusinessRolesService.updateMany(userBusinessRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<UserBusinessRoles>> filterUserBusinessRoles(@RequestBody UserBusinessRolesVo.PublicUserBusinessRoles userBusinessRolesVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		UserBusinessRoles userBusinessRoles = ObjectMapperUtils.getObjectMapper().convertValue(userBusinessRolesVo, new TypeReference<UserBusinessRoles>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(userBusinessRoles);
		log.info("queryMap: {}", queryMap);
		List<UserBusinessRoles> userBusinessRolesList = userBusinessRolesService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(userBusinessRolesList);
	}

 private UserBusinessRoles setFks(UserBusinessRoles userBusinessRoles) {

 CommonUtils.optionalChaining(()-> userBusinessRoles.getBusinessRoles().getBusinessRolesId())
 .map(_businessRolesId -> businessRolesService.findById(_businessRolesId))
 .ifPresent(_businessRoles -> userBusinessRoles.setBusinessRoles(_businessRoles.get()));

 CommonUtils.optionalChaining(()-> userBusinessRoles.getProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> userBusinessRoles.setProject(_project.get()));

 return userBusinessRoles;

 }

}
