

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
@RequestMapping("/UserTypeDetail")
@Slf4j
@Validated
@Tag(name = "UserTypeDetail_Controller")
public class UserTypeDetailController {

 @Autowired
 private UserTypeDetailService<UserTypeDetail> userTypeDetailService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<UserTypeDetail> findUserTypeDetailById(Integer id) {
		UserTypeDetail userTypeDetail = userTypeDetailService.findById(id).get();
		return ResponseHandler.getResponse(userTypeDetail);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<UserTypeDetail>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<UserTypeDetail> userTypeDetail = userTypeDetailService.findWithPaging(page, size);
		return ResponseHandler.getResponse(userTypeDetail);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteUserTypeDetail(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = userTypeDetailService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<UserTypeDetail>> createUserTypeDetail(@RequestBody @Valid List<UserTypeDetailVo.SaveUserTypeDetail> userTypeDetailVos) {
		List<UserTypeDetail> userTypeDetails = userTypeDetailVos.stream().map( userTypeDetailVo -> {
			UserTypeDetail userTypeDetail = CloneUtils.getClone(new UserTypeDetail(), userTypeDetailVo);
      			return userTypeDetail;
		}).collect(Collectors.toList());
		List<UserTypeDetail> response = userTypeDetailService.createMany(userTypeDetails);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<UserTypeDetail>> updateUserTypeDetail(@RequestBody List<UserTypeDetailVo.UpdateUserTypeDetail> userTypeDetailVos) {
		List<UserTypeDetail> userTypeDetails = userTypeDetailVos.stream().map( userTypeDetailVo -> {
			UserTypeDetail foundUserTypeDetail = userTypeDetailService.findById(userTypeDetailVo.getUserTypeDetailId()).get();
			UserTypeDetail userTypeDetail = CloneUtils.getClone(foundUserTypeDetail, userTypeDetailVo);
      			return userTypeDetail;
		}).collect(Collectors.toList());

		List<UserTypeDetail> response = userTypeDetailService.updateMany(userTypeDetails);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<UserTypeDetail>> filterUserTypeDetail(@RequestBody UserTypeDetailVo.PublicUserTypeDetail userTypeDetailVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		UserTypeDetail userTypeDetail = ObjectMapperUtils.getObjectMapper().convertValue(userTypeDetailVo, new TypeReference<UserTypeDetail>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(userTypeDetail);
		log.info("queryMap: {}", queryMap);
		List<UserTypeDetail> userTypeDetailList = userTypeDetailService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(userTypeDetailList);
	}

}
