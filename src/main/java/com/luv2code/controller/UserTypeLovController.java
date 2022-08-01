

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
@RequestMapping("/UserTypeLov")
@Slf4j
@Validated
@Tag(name = "UserTypeLov_Controller")
public class UserTypeLovController {

 @Autowired
 private UserTypeLovService<UserTypeLov> userTypeLovService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<UserTypeLov> findUserTypeLovById(Integer id) {
		UserTypeLov userTypeLov = userTypeLovService.findById(id).get();
		return ResponseHandler.getResponse(userTypeLov);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<UserTypeLov>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<UserTypeLov> userTypeLov = userTypeLovService.findWithPaging(page, size);
		return ResponseHandler.getResponse(userTypeLov);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUserTypeLov(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = userTypeLovService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<UserTypeLov>> createUserTypeLov(@RequestBody @Valid List<UserTypeLovVo.SaveUserTypeLov> userTypeLovVos) {
		List<UserTypeLov> userTypeLovs = userTypeLovVos.stream().map( userTypeLovVo -> {
			UserTypeLov userTypeLov = CloneUtils.getClone(new UserTypeLov(), userTypeLovVo);
      			return userTypeLov;
		}).collect(Collectors.toList());
		List<UserTypeLov> response = userTypeLovService.createMany(userTypeLovs);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<UserTypeLov>> updateUserTypeLov(@RequestBody List<UserTypeLovVo.UpdateUserTypeLov> userTypeLovVos) {
		List<UserTypeLov> userTypeLovs = userTypeLovVos.stream().map( userTypeLovVo -> {
			UserTypeLov foundUserTypeLov = userTypeLovService.findById(userTypeLovVo.getUserTypeLovId()).get();
			UserTypeLov userTypeLov = CloneUtils.getClone(foundUserTypeLov, userTypeLovVo);
      			return userTypeLov;
		}).collect(Collectors.toList());

		List<UserTypeLov> response = userTypeLovService.updateMany(userTypeLovs);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<UserTypeLov>> filterUserTypeLov(@RequestBody UserTypeLovVo.PublicUserTypeLov userTypeLovVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		UserTypeLov userTypeLov = ObjectMapperUtils.getObjectMapper().convertValue(userTypeLovVo, new TypeReference<UserTypeLov>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(userTypeLov);
		log.info("queryMap: {}", queryMap);
		List<UserTypeLov> userTypeLovList = userTypeLovService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(userTypeLovList);
	}

}
