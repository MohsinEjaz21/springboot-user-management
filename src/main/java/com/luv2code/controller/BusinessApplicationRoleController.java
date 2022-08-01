

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
@RequestMapping("/BusinessApplicationRole")
@Slf4j
@Validated
@Tag(name = "BusinessApplicationRole_Controller")
public class BusinessApplicationRoleController {

 @Autowired
 private BusinessApplicationRoleService<BusinessApplicationRole> businessApplicationRoleService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<BusinessApplicationRole> findBusinessApplicationRoleById(Integer id) {
		BusinessApplicationRole businessApplicationRole = businessApplicationRoleService.findById(id).get();
		return ResponseHandler.getResponse(businessApplicationRole);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<BusinessApplicationRole>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<BusinessApplicationRole> businessApplicationRole = businessApplicationRoleService.findWithPaging(page, size);
		return ResponseHandler.getResponse(businessApplicationRole);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteBusinessApplicationRole(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = businessApplicationRoleService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<BusinessApplicationRole>> createBusinessApplicationRole(@RequestBody @Valid List<BusinessApplicationRoleVo.SaveBusinessApplicationRole> businessApplicationRoleVos) {
		List<BusinessApplicationRole> businessApplicationRoles = businessApplicationRoleVos.stream().map( businessApplicationRoleVo -> {
			BusinessApplicationRole businessApplicationRole = CloneUtils.getClone(new BusinessApplicationRole(), businessApplicationRoleVo);
      			return businessApplicationRole;
		}).collect(Collectors.toList());
		List<BusinessApplicationRole> response = businessApplicationRoleService.createMany(businessApplicationRoles);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<BusinessApplicationRole>> updateBusinessApplicationRole(@RequestBody List<BusinessApplicationRoleVo.UpdateBusinessApplicationRole> businessApplicationRoleVos) {
		List<BusinessApplicationRole> businessApplicationRoles = businessApplicationRoleVos.stream().map( businessApplicationRoleVo -> {
			BusinessApplicationRole foundBusinessApplicationRole = businessApplicationRoleService.findById(businessApplicationRoleVo.getBusinessApplicationRoleId()).get();
			BusinessApplicationRole businessApplicationRole = CloneUtils.getClone(foundBusinessApplicationRole, businessApplicationRoleVo);
      			return businessApplicationRole;
		}).collect(Collectors.toList());

		List<BusinessApplicationRole> response = businessApplicationRoleService.updateMany(businessApplicationRoles);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<BusinessApplicationRole>> filterBusinessApplicationRole(@RequestBody BusinessApplicationRoleVo.PublicBusinessApplicationRole businessApplicationRoleVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		BusinessApplicationRole businessApplicationRole = ObjectMapperUtils.getObjectMapper().convertValue(businessApplicationRoleVo, new TypeReference<BusinessApplicationRole>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(businessApplicationRole);
		log.info("queryMap: {}", queryMap);
		List<BusinessApplicationRole> businessApplicationRoleList = businessApplicationRoleService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(businessApplicationRoleList);
	}

}
