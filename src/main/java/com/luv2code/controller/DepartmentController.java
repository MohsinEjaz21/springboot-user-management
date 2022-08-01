

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
@RequestMapping("/Department")
@Slf4j
@Validated
@Tag(name = "Department_Controller")
public class DepartmentController {

 @Autowired
 private ClientService<Client> clientService;

 @Autowired
 private DepartmentService<Department> departmentService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Department> findDepartmentById(Integer id) {
		Department department = departmentService.findById(id).get();
		return ResponseHandler.getResponse(department);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Department>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Department> department = departmentService.findWithPaging(page, size);
		return ResponseHandler.getResponse(department);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteDepartment(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = departmentService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Department>> createDepartment(@RequestBody @Valid List<DepartmentVo.SaveDepartment> departmentVos) {
		List<Department> departments = departmentVos.stream().map( departmentVo -> {
			Department department = CloneUtils.getClone(new Department(), departmentVo);
      			setFks(department);
      			return department;
		}).collect(Collectors.toList());
		List<Department> response = departmentService.createMany(departments);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Department>> updateDepartment(@RequestBody List<DepartmentVo.UpdateDepartment> departmentVos) {
		List<Department> departments = departmentVos.stream().map( departmentVo -> {
			Department foundDepartment = departmentService.findById(departmentVo.getDepartmentId()).get();
			Department department = CloneUtils.getClone(foundDepartment, departmentVo);
      			setFks(department);
      			return department;
		}).collect(Collectors.toList());

		List<Department> response = departmentService.updateMany(departments);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Department>> filterDepartment(@RequestBody DepartmentVo.PublicDepartment departmentVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Department department = ObjectMapperUtils.getObjectMapper().convertValue(departmentVo, new TypeReference<Department>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(department);
		log.info("queryMap: {}", queryMap);
		List<Department> departmentList = departmentService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(departmentList);
	}

 private Department setFks(Department department) {

 CommonUtils.optionalChaining(()-> department.getClient().getClientId())
 .map(_clientId -> clientService.findById(_clientId))
 .ifPresent(_client -> department.setClient(_client.get()));

 return department;

 }

}
