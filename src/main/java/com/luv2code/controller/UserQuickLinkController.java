

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
@RequestMapping("/UserQuickLink")
@Slf4j
@Validated
@Tag(name = "UserQuickLink_Controller")
public class UserQuickLinkController {

 @Autowired
 private ApplicationRolesService<ApplicationRoles> applicationRolesService;

 @Autowired
 private UserQuickLinkService<UserQuickLink> userQuickLinkService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<UserQuickLink> findUserQuickLinkById(Integer id) {
		UserQuickLink userQuickLink = userQuickLinkService.findById(id).get();
		return ResponseHandler.getResponse(userQuickLink);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<UserQuickLink>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<UserQuickLink> userQuickLink = userQuickLinkService.findWithPaging(page, size);
		return ResponseHandler.getResponse(userQuickLink);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUserQuickLink(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = userQuickLinkService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<UserQuickLink>> createUserQuickLink(@RequestBody @Valid List<UserQuickLinkVo.SaveUserQuickLink> userQuickLinkVos) {
		List<UserQuickLink> userQuickLinks = userQuickLinkVos.stream().map( userQuickLinkVo -> {
			UserQuickLink userQuickLink = CloneUtils.getClone(new UserQuickLink(), userQuickLinkVo);
      			setFks(userQuickLink);
      			return userQuickLink;
		}).collect(Collectors.toList());
		List<UserQuickLink> response = userQuickLinkService.createMany(userQuickLinks);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<UserQuickLink>> updateUserQuickLink(@RequestBody List<UserQuickLinkVo.UpdateUserQuickLink> userQuickLinkVos) {
		List<UserQuickLink> userQuickLinks = userQuickLinkVos.stream().map( userQuickLinkVo -> {
			UserQuickLink foundUserQuickLink = userQuickLinkService.findById(userQuickLinkVo.getUserQuickLinkId()).get();
			UserQuickLink userQuickLink = CloneUtils.getClone(foundUserQuickLink, userQuickLinkVo);
      			setFks(userQuickLink);
      			return userQuickLink;
		}).collect(Collectors.toList());

		List<UserQuickLink> response = userQuickLinkService.updateMany(userQuickLinks);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<UserQuickLink>> filterUserQuickLink(@RequestBody UserQuickLinkVo.PublicUserQuickLink userQuickLinkVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		UserQuickLink userQuickLink = ObjectMapperUtils.getObjectMapper().convertValue(userQuickLinkVo, new TypeReference<UserQuickLink>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(userQuickLink);
		log.info("queryMap: {}", queryMap);
		List<UserQuickLink> userQuickLinkList = userQuickLinkService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(userQuickLinkList);
	}

 private UserQuickLink setFks(UserQuickLink userQuickLink) {

 CommonUtils.optionalChaining(()-> userQuickLink.getApplicationRole().getApplicationRolesId())
 .map(_applicationRolesId -> applicationRolesService.findById(_applicationRolesId))
 .ifPresent(_applicationRoles -> userQuickLink.setApplicationRole(_applicationRoles.get()));

 return userQuickLink;

 }

}
