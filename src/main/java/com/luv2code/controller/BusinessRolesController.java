

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
@RequestMapping("/BusinessRoles")
@Slf4j
@Validated
@Tag(name = "BusinessRoles_Controller")
public class BusinessRolesController {

 @Autowired
 private BusinessRolesService<BusinessRoles> businessRolesService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<BusinessRoles> findBusinessRolesById(Integer id) {
		BusinessRoles businessRoles = businessRolesService.findById(id).get();
		return ResponseHandler.getResponse(businessRoles);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<BusinessRoles>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<BusinessRoles> businessRoles = businessRolesService.findWithPaging(page, size);
		return ResponseHandler.getResponse(businessRoles);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteBusinessRoles(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = businessRolesService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<BusinessRoles>> createBusinessRoles(@RequestBody @Valid List<BusinessRolesVo.SaveBusinessRoles> businessRolesVos) {
		List<BusinessRoles> businessRoless = businessRolesVos.stream().map( businessRolesVo -> {
			BusinessRoles businessRoles = CloneUtils.getClone(new BusinessRoles(), businessRolesVo);
      			return businessRoles;
		}).collect(Collectors.toList());
		List<BusinessRoles> response = businessRolesService.createMany(businessRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<BusinessRoles>> updateBusinessRoles(@RequestBody List<BusinessRolesVo.UpdateBusinessRoles> businessRolesVos) {
		List<BusinessRoles> businessRoless = businessRolesVos.stream().map( businessRolesVo -> {
			BusinessRoles foundBusinessRoles = businessRolesService.findById(businessRolesVo.getBusinessRolesId()).get();
			BusinessRoles businessRoles = CloneUtils.getClone(foundBusinessRoles, businessRolesVo);
      			return businessRoles;
		}).collect(Collectors.toList());

		List<BusinessRoles> response = businessRolesService.updateMany(businessRoless);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<BusinessRoles>> filterBusinessRoles(@RequestBody BusinessRolesVo.PublicBusinessRoles businessRolesVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		BusinessRoles businessRoles = ObjectMapperUtils.getObjectMapper().convertValue(businessRolesVo, new TypeReference<BusinessRoles>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(businessRoles);
		log.info("queryMap: {}", queryMap);
		List<BusinessRoles> businessRolesList = businessRolesService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(businessRolesList);
	}

}
