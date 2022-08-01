

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
@RequestMapping("/UserDepartment")
@Slf4j
@Validated
@Tag(name = "UserDepartment_Controller")
public class UserDepartmentController {

 @Autowired
 private UserDepartmentService<UserDepartment> userDepartmentService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<UserDepartment> findUserDepartmentById(Integer id) {
		UserDepartment userDepartment = userDepartmentService.findById(id).get();
		return ResponseHandler.getResponse(userDepartment);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<UserDepartment>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<UserDepartment> userDepartment = userDepartmentService.findWithPaging(page, size);
		return ResponseHandler.getResponse(userDepartment);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUserDepartment(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = userDepartmentService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<UserDepartment>> createUserDepartment(@RequestBody @Valid List<UserDepartmentVo.SaveUserDepartment> userDepartmentVos) {
		List<UserDepartment> userDepartments = userDepartmentVos.stream().map( userDepartmentVo -> {
			UserDepartment userDepartment = CloneUtils.getClone(new UserDepartment(), userDepartmentVo);
      			return userDepartment;
		}).collect(Collectors.toList());
		List<UserDepartment> response = userDepartmentService.createMany(userDepartments);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<UserDepartment>> updateUserDepartment(@RequestBody List<UserDepartmentVo.UpdateUserDepartment> userDepartmentVos) {
		List<UserDepartment> userDepartments = userDepartmentVos.stream().map( userDepartmentVo -> {
			UserDepartment foundUserDepartment = userDepartmentService.findById(userDepartmentVo.getUserDepartmentId()).get();
			UserDepartment userDepartment = CloneUtils.getClone(foundUserDepartment, userDepartmentVo);
      			return userDepartment;
		}).collect(Collectors.toList());

		List<UserDepartment> response = userDepartmentService.updateMany(userDepartments);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<UserDepartment>> filterUserDepartment(@RequestBody UserDepartmentVo.PublicUserDepartment userDepartmentVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		UserDepartment userDepartment = ObjectMapperUtils.getObjectMapper().convertValue(userDepartmentVo, new TypeReference<UserDepartment>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(userDepartment);
		log.info("queryMap: {}", queryMap);
		List<UserDepartment> userDepartmentList = userDepartmentService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(userDepartmentList);
	}

}
