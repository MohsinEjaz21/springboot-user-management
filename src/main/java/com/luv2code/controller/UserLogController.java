

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
@RequestMapping("/UserLog")
@Slf4j
@Validated
@Tag(name = "UserLog_Controller")
public class UserLogController {

 @Autowired
 private UsersService<Users> usersService;

 @Autowired
 private UserLogService<UserLog> userLogService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<UserLog> findUserLogById(Integer id) {
		UserLog userLog = userLogService.findById(id).get();
		return ResponseHandler.getResponse(userLog);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<UserLog>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<UserLog> userLog = userLogService.findWithPaging(page, size);
		return ResponseHandler.getResponse(userLog);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUserLog(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = userLogService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<UserLog>> createUserLog(@RequestBody @Valid List<UserLogVo.SaveUserLog> userLogVos) {
		List<UserLog> userLogs = userLogVos.stream().map( userLogVo -> {
			UserLog userLog = CloneUtils.getClone(new UserLog(), userLogVo);
      			setFks(userLog);
      			return userLog;
		}).collect(Collectors.toList());
		List<UserLog> response = userLogService.createMany(userLogs);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<UserLog>> updateUserLog(@RequestBody List<UserLogVo.UpdateUserLog> userLogVos) {
		List<UserLog> userLogs = userLogVos.stream().map( userLogVo -> {
			UserLog foundUserLog = userLogService.findById(userLogVo.getUserLogId()).get();
			UserLog userLog = CloneUtils.getClone(foundUserLog, userLogVo);
      			setFks(userLog);
      			return userLog;
		}).collect(Collectors.toList());

		List<UserLog> response = userLogService.updateMany(userLogs);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<UserLog>> filterUserLog(@RequestBody UserLogVo.PublicUserLog userLogVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		UserLog userLog = ObjectMapperUtils.getObjectMapper().convertValue(userLogVo, new TypeReference<UserLog>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(userLog);
		log.info("queryMap: {}", queryMap);
		List<UserLog> userLogList = userLogService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(userLogList);
	}

 private UserLog setFks(UserLog userLog) {

 CommonUtils.optionalChaining(()-> userLog.getUser().getUsersId())
 .map(_usersId -> usersService.findById(_usersId))
 .ifPresent(_users -> userLog.setUser(_users.get()));

 return userLog;

 }

}
