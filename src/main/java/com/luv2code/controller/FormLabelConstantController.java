

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
@RequestMapping("/FormLabelConstant")
@Slf4j
@Validated
@Tag(name = "FormLabelConstant_Controller")
public class FormLabelConstantController {

 @Autowired
 private ApplicationRolesService<ApplicationRoles> applicationRolesService;
 @Autowired
 private LabelConstantsService<LabelConstants> labelConstantsService;

 @Autowired
 private FormLabelConstantService<FormLabelConstant> formLabelConstantService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<FormLabelConstant> findFormLabelConstantById(Integer id) {
		FormLabelConstant formLabelConstant = formLabelConstantService.findById(id).get();
		return ResponseHandler.getResponse(formLabelConstant);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<FormLabelConstant>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<FormLabelConstant> formLabelConstant = formLabelConstantService.findWithPaging(page, size);
		return ResponseHandler.getResponse(formLabelConstant);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteFormLabelConstant(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = formLabelConstantService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<FormLabelConstant>> createFormLabelConstant(@RequestBody @Valid List<FormLabelConstantVo.SaveFormLabelConstant> formLabelConstantVos) {
		List<FormLabelConstant> formLabelConstants = formLabelConstantVos.stream().map( formLabelConstantVo -> {
			FormLabelConstant formLabelConstant = CloneUtils.getClone(new FormLabelConstant(), formLabelConstantVo);
      			setFks(formLabelConstant);
      			return formLabelConstant;
		}).collect(Collectors.toList());
		List<FormLabelConstant> response = formLabelConstantService.createMany(formLabelConstants);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<FormLabelConstant>> updateFormLabelConstant(@RequestBody List<FormLabelConstantVo.UpdateFormLabelConstant> formLabelConstantVos) {
		List<FormLabelConstant> formLabelConstants = formLabelConstantVos.stream().map( formLabelConstantVo -> {
			FormLabelConstant foundFormLabelConstant = formLabelConstantService.findById(formLabelConstantVo.getFormLabelConstantId()).get();
			FormLabelConstant formLabelConstant = CloneUtils.getClone(foundFormLabelConstant, formLabelConstantVo);
      			setFks(formLabelConstant);
      			return formLabelConstant;
		}).collect(Collectors.toList());

		List<FormLabelConstant> response = formLabelConstantService.updateMany(formLabelConstants);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<FormLabelConstant>> filterFormLabelConstant(@RequestBody FormLabelConstantVo.PublicFormLabelConstant formLabelConstantVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		FormLabelConstant formLabelConstant = ObjectMapperUtils.getObjectMapper().convertValue(formLabelConstantVo, new TypeReference<FormLabelConstant>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(formLabelConstant);
		log.info("queryMap: {}", queryMap);
		List<FormLabelConstant> formLabelConstantList = formLabelConstantService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(formLabelConstantList);
	}

 private FormLabelConstant setFks(FormLabelConstant formLabelConstant) {

 CommonUtils.optionalChaining(()-> formLabelConstant.getForm().getApplicationRolesId())
 .map(_applicationRolesId -> applicationRolesService.findById(_applicationRolesId))
 .ifPresent(_applicationRoles -> formLabelConstant.setForm(_applicationRoles.get()));

 CommonUtils.optionalChaining(()-> formLabelConstant.getConstant().getLabelConstantsId())
 .map(_labelConstantsId -> labelConstantsService.findById(_labelConstantsId))
 .ifPresent(_labelConstants -> formLabelConstant.setConstant(_labelConstants.get()));

 return formLabelConstant;

 }

}
