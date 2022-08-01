

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
@RequestMapping("/ConstantLanguage")
@Slf4j
@Validated
@Tag(name = "ConstantLanguage_Controller")
public class ConstantLanguageController {

 @Autowired
 private LabelConstantsService<LabelConstants> labelConstantsService;
 @Autowired
 private LanguageService<Language> languageService;

 @Autowired
 private ConstantLanguageService<ConstantLanguage> constantLanguageService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<ConstantLanguage> findConstantLanguageById(Integer id) {
		ConstantLanguage constantLanguage = constantLanguageService.findById(id).get();
		return ResponseHandler.getResponse(constantLanguage);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<ConstantLanguage>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<ConstantLanguage> constantLanguage = constantLanguageService.findWithPaging(page, size);
		return ResponseHandler.getResponse(constantLanguage);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteConstantLanguage(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = constantLanguageService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<ConstantLanguage>> createConstantLanguage(@RequestBody @Valid List<ConstantLanguageVo.SaveConstantLanguage> constantLanguageVos) {
		List<ConstantLanguage> constantLanguages = constantLanguageVos.stream().map( constantLanguageVo -> {
			ConstantLanguage constantLanguage = CloneUtils.getClone(new ConstantLanguage(), constantLanguageVo);
      			setFks(constantLanguage);
      			return constantLanguage;
		}).collect(Collectors.toList());
		List<ConstantLanguage> response = constantLanguageService.createMany(constantLanguages);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<ConstantLanguage>> updateConstantLanguage(@RequestBody List<ConstantLanguageVo.UpdateConstantLanguage> constantLanguageVos) {
		List<ConstantLanguage> constantLanguages = constantLanguageVos.stream().map( constantLanguageVo -> {
			ConstantLanguage foundConstantLanguage = constantLanguageService.findById(constantLanguageVo.getConstantLanguageId()).get();
			ConstantLanguage constantLanguage = CloneUtils.getClone(foundConstantLanguage, constantLanguageVo);
      			setFks(constantLanguage);
      			return constantLanguage;
		}).collect(Collectors.toList());

		List<ConstantLanguage> response = constantLanguageService.updateMany(constantLanguages);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<ConstantLanguage>> filterConstantLanguage(@RequestBody ConstantLanguageVo.PublicConstantLanguage constantLanguageVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		ConstantLanguage constantLanguage = ObjectMapperUtils.getObjectMapper().convertValue(constantLanguageVo, new TypeReference<ConstantLanguage>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(constantLanguage);
		log.info("queryMap: {}", queryMap);
		List<ConstantLanguage> constantLanguageList = constantLanguageService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(constantLanguageList);
	}

 private ConstantLanguage setFks(ConstantLanguage constantLanguage) {

 CommonUtils.optionalChaining(()-> constantLanguage.getConstant().getLabelConstantsId())
 .map(_labelConstantsId -> labelConstantsService.findById(_labelConstantsId))
 .ifPresent(_labelConstants -> constantLanguage.setConstant(_labelConstants.get()));

 CommonUtils.optionalChaining(()-> constantLanguage.getLanguage().getLanguageId())
 .map(_languageId -> languageService.findById(_languageId))
 .ifPresent(_language -> constantLanguage.setLanguage(_language.get()));

 return constantLanguage;

 }

}
