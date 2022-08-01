

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
@RequestMapping("/DepartmentRoles")
@Slf4j
@Validated
@Tag(name = "DepartmentRoles_Controller")
public class DepartmentRolesController {

 @Autowired
 private DepartmentService<Department> departmentService;
 @Autowired
 private BusinessRolesService<BusinessRoles> businessRolesService;

 @Autowired
 private DepartmentRolesService<DepartmentRoles> departmentRolesService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<DepartmentRoles> findDepartmentRolesById(Integer id) {
		DepartmentRoles departmentRoles = departmentRolesService.findById(id).get();
		return ResponseHandler.getResponse(departmentRoles);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<DepartmentRoles>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<DepartmentRoles> departmentRoles = departmentRolesService.findWithPaging(page, size);
		return ResponseHandler.getResponse(departmentRoles);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteDepartmentRoles(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = departmentRolesService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<DepartmentRoles>> createDepartmentRoles(@RequestBody @Valid List<DepartmentRolesVo.SaveDepartmentRoles> departmentRolesVos) {
		List<DepartmentRoles> departmentRoless = departmentRolesVos.stream().map( departmentRolesVo -> {
			DepartmentRoles departmentRoles = CloneUtils.getClone(new DepartmentRoles(), departmentRolesVo);
      			setFks(departmentRoles);
      			return departmentRoles;
		}).collect(Collectors.toList());
		List<DepartmentRoles> response = departmentRolesService.createMany(departmentRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<DepartmentRoles>> updateDepartmentRoles(@RequestBody List<DepartmentRolesVo.UpdateDepartmentRoles> departmentRolesVos) {
		List<DepartmentRoles> departmentRoless = departmentRolesVos.stream().map( departmentRolesVo -> {
			DepartmentRoles foundDepartmentRoles = departmentRolesService.findById(departmentRolesVo.getDepartmentRolesId()).get();
			DepartmentRoles departmentRoles = CloneUtils.getClone(foundDepartmentRoles, departmentRolesVo);
      			setFks(departmentRoles);
      			return departmentRoles;
		}).collect(Collectors.toList());

		List<DepartmentRoles> response = departmentRolesService.updateMany(departmentRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<DepartmentRoles>> filterDepartmentRoles(@RequestBody DepartmentRolesVo.PublicDepartmentRoles departmentRolesVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		DepartmentRoles departmentRoles = ObjectMapperUtils.getObjectMapper().convertValue(departmentRolesVo, new TypeReference<DepartmentRoles>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(departmentRoles);
		log.info("queryMap: {}", queryMap);
		List<DepartmentRoles> departmentRolesList = departmentRolesService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(departmentRolesList);
	}

 private DepartmentRoles setFks(DepartmentRoles departmentRoles) {

 CommonUtils.optionalChaining(()-> departmentRoles.getDepartment().getDepartmentId())
 .map(_departmentId -> departmentService.findById(_departmentId))
 .ifPresent(_department -> departmentRoles.setDepartment(_department.get()));

 CommonUtils.optionalChaining(()-> departmentRoles.getBusinessRole().getBusinessRolesId())
 .map(_businessRolesId -> businessRolesService.findById(_businessRolesId))
 .ifPresent(_businessRoles -> departmentRoles.setBusinessRole(_businessRoles.get()));

 return departmentRoles;

 }

}
