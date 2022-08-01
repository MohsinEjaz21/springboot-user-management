

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
@RequestMapping("/Users")
@Slf4j
@Validated
@Tag(name = "Users_Controller")
public class UsersController {

 @Autowired
 private BusinessRolesService<BusinessRoles> businessRolesService;
 @Autowired
 private ClientService<Client> clientService;
 @Autowired
 private DepartmentService<Department> departmentService;
 @Autowired
 private UserTypeLovService<UserTypeLov> userTypeLovService;

 @Autowired
 private UsersService<Users> usersService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Users> findUsersById(Integer id) {
		Users users = usersService.findById(id).get();
		return ResponseHandler.getResponse(users);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Users>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Users> users = usersService.findWithPaging(page, size);
		return ResponseHandler.getResponse(users);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUsers(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = usersService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Users>> createUsers(@RequestBody @Valid List<UsersVo.SaveUsers> usersVos) {
		List<Users> userss = usersVos.stream().map( usersVo -> {
			Users users = CloneUtils.getClone(new Users(), usersVo);
      			setFks(users);
      			return users;
		}).collect(Collectors.toList());
		List<Users> response = usersService.createMany(userss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Users>> updateUsers(@RequestBody List<UsersVo.UpdateUsers> usersVos) {
		List<Users> userss = usersVos.stream().map( usersVo -> {
			Users foundUsers = usersService.findById(usersVo.getUsersId()).get();
			Users users = CloneUtils.getClone(foundUsers, usersVo);
      			setFks(users);
      			return users;
		}).collect(Collectors.toList());

		List<Users> response = usersService.updateMany(userss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Users>> filterUsers(@RequestBody UsersVo.PublicUsers usersVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Users users = ObjectMapperUtils.getObjectMapper().convertValue(usersVo, new TypeReference<Users>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(users);
		log.info("queryMap: {}", queryMap);
		List<Users> usersList = usersService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(usersList);
	}

 private Users setFks(Users users) {

 CommonUtils.optionalChaining(()-> users.getBusinessRole().getBusinessRolesId())
 .map(_businessRolesId -> businessRolesService.findById(_businessRolesId))
 .ifPresent(_businessRoles -> users.setBusinessRole(_businessRoles.get()));

 CommonUtils.optionalChaining(()-> users.getClient().getClientId())
 .map(_clientId -> clientService.findById(_clientId))
 .ifPresent(_client -> users.setClient(_client.get()));

 CommonUtils.optionalChaining(()-> users.getDepartment().getDepartmentId())
 .map(_departmentId -> departmentService.findById(_departmentId))
 .ifPresent(_department -> users.setDepartment(_department.get()));

 CommonUtils.optionalChaining(()-> users.getUserTypeLov().getUserTypeLovId())
 .map(_userTypeLovId -> userTypeLovService.findById(_userTypeLovId))
 .ifPresent(_userTypeLov -> users.setUserTypeLov(_userTypeLov.get()));

 return users;

 }

}
