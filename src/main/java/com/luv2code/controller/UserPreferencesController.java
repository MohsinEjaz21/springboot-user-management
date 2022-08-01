

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
@RequestMapping("/UserPreferences")
@Slf4j
@Validated
@Tag(name = "UserPreferences_Controller")
public class UserPreferencesController {

 @Autowired
 private LanguageService<Language> languageService;
 @Autowired
 private UsersService<Users> usersService;
 @Autowired
 private ProjectService<Project> projectService;
 @Autowired
 private BusinessRolesService<BusinessRoles> businessRolesService;

 @Autowired
 private UserPreferencesService<UserPreferences> userPreferencesService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<UserPreferences> findUserPreferencesById(Integer id) {
		UserPreferences userPreferences = userPreferencesService.findById(id).get();
		return ResponseHandler.getResponse(userPreferences);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<UserPreferences>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<UserPreferences> userPreferences = userPreferencesService.findWithPaging(page, size);
		return ResponseHandler.getResponse(userPreferences);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUserPreferences(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = userPreferencesService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<UserPreferences>> createUserPreferences(@RequestBody @Valid List<UserPreferencesVo.SaveUserPreferences> userPreferencesVos) {
		List<UserPreferences> userPreferencess = userPreferencesVos.stream().map( userPreferencesVo -> {
			UserPreferences userPreferences = CloneUtils.getClone(new UserPreferences(), userPreferencesVo);
      			setFks(userPreferences);
      			return userPreferences;
		}).collect(Collectors.toList());
		List<UserPreferences> response = userPreferencesService.createMany(userPreferencess);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<UserPreferences>> updateUserPreferences(@RequestBody List<UserPreferencesVo.UpdateUserPreferences> userPreferencesVos) {
		List<UserPreferences> userPreferencess = userPreferencesVos.stream().map( userPreferencesVo -> {
			UserPreferences foundUserPreferences = userPreferencesService.findById(userPreferencesVo.getUserPreferencesId()).get();
			UserPreferences userPreferences = CloneUtils.getClone(foundUserPreferences, userPreferencesVo);
      			setFks(userPreferences);
      			return userPreferences;
		}).collect(Collectors.toList());

		List<UserPreferences> response = userPreferencesService.updateMany(userPreferencess);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<UserPreferences>> filterUserPreferences(@RequestBody UserPreferencesVo.PublicUserPreferences userPreferencesVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		UserPreferences userPreferences = ObjectMapperUtils.getObjectMapper().convertValue(userPreferencesVo, new TypeReference<UserPreferences>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(userPreferences);
		log.info("queryMap: {}", queryMap);
		List<UserPreferences> userPreferencesList = userPreferencesService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(userPreferencesList);
	}

 private UserPreferences setFks(UserPreferences userPreferences) {

 CommonUtils.optionalChaining(()-> userPreferences.getDefaultLanguage().getLanguageId())
 .map(_languageId -> languageService.findById(_languageId))
 .ifPresent(_language -> userPreferences.setDefaultLanguage(_language.get()));

 CommonUtils.optionalChaining(()-> userPreferences.getUser().getUsersId())
 .map(_usersId -> usersService.findById(_usersId))
 .ifPresent(_users -> userPreferences.setUser(_users.get()));

 CommonUtils.optionalChaining(()-> userPreferences.getDefaultProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> userPreferences.setDefaultProject(_project.get()));

 CommonUtils.optionalChaining(()-> userPreferences.getDefaultProjectRole().getBusinessRolesId())
 .map(_businessRolesId -> businessRolesService.findById(_businessRolesId))
 .ifPresent(_businessRoles -> userPreferences.setDefaultProjectRole(_businessRoles.get()));

 return userPreferences;

 }

}
