

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
@RequestMapping("/FormLabelConstantTranslation")
@Slf4j
@Validated
@Tag(name = "FormLabelConstantTranslation_Controller")
public class FormLabelConstantTranslationController {

 @Autowired
 private FormLabelConstantService<FormLabelConstant> formLabelConstantService;
 @Autowired
 private LanguageService<Language> languageService;

 @Autowired
 private FormLabelConstantTranslationService<FormLabelConstantTranslation> formLabelConstantTranslationService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<FormLabelConstantTranslation> findFormLabelConstantTranslationById(Integer id) {
		FormLabelConstantTranslation formLabelConstantTranslation = formLabelConstantTranslationService.findById(id).get();
		return ResponseHandler.getResponse(formLabelConstantTranslation);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<FormLabelConstantTranslation>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<FormLabelConstantTranslation> formLabelConstantTranslation = formLabelConstantTranslationService.findWithPaging(page, size);
		return ResponseHandler.getResponse(formLabelConstantTranslation);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteFormLabelConstantTranslation(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = formLabelConstantTranslationService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<FormLabelConstantTranslation>> createFormLabelConstantTranslation(@RequestBody @Valid List<FormLabelConstantTranslationVo.SaveFormLabelConstantTranslation> formLabelConstantTranslationVos) {
		List<FormLabelConstantTranslation> formLabelConstantTranslations = formLabelConstantTranslationVos.stream().map( formLabelConstantTranslationVo -> {
			FormLabelConstantTranslation formLabelConstantTranslation = CloneUtils.getClone(new FormLabelConstantTranslation(), formLabelConstantTranslationVo);
      			setFks(formLabelConstantTranslation);
      			return formLabelConstantTranslation;
		}).collect(Collectors.toList());
		List<FormLabelConstantTranslation> response = formLabelConstantTranslationService.createMany(formLabelConstantTranslations);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<FormLabelConstantTranslation>> updateFormLabelConstantTranslation(@RequestBody List<FormLabelConstantTranslationVo.UpdateFormLabelConstantTranslation> formLabelConstantTranslationVos) {
		List<FormLabelConstantTranslation> formLabelConstantTranslations = formLabelConstantTranslationVos.stream().map( formLabelConstantTranslationVo -> {
			FormLabelConstantTranslation foundFormLabelConstantTranslation = formLabelConstantTranslationService.findById(formLabelConstantTranslationVo.getFormLabelConstantTranslationId()).get();
			FormLabelConstantTranslation formLabelConstantTranslation = CloneUtils.getClone(foundFormLabelConstantTranslation, formLabelConstantTranslationVo);
      			setFks(formLabelConstantTranslation);
      			return formLabelConstantTranslation;
		}).collect(Collectors.toList());

		List<FormLabelConstantTranslation> response = formLabelConstantTranslationService.updateMany(formLabelConstantTranslations);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<FormLabelConstantTranslation>> filterFormLabelConstantTranslation(@RequestBody FormLabelConstantTranslationVo.PublicFormLabelConstantTranslation formLabelConstantTranslationVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		FormLabelConstantTranslation formLabelConstantTranslation = ObjectMapperUtils.getObjectMapper().convertValue(formLabelConstantTranslationVo, new TypeReference<FormLabelConstantTranslation>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(formLabelConstantTranslation);
		log.info("queryMap: {}", queryMap);
		List<FormLabelConstantTranslation> formLabelConstantTranslationList = formLabelConstantTranslationService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(formLabelConstantTranslationList);
	}

 private FormLabelConstantTranslation setFks(FormLabelConstantTranslation formLabelConstantTranslation) {

 CommonUtils.optionalChaining(()-> formLabelConstantTranslation.getFormLabelConstant().getFormLabelConstantId())
 .map(_formLabelConstantId -> formLabelConstantService.findById(_formLabelConstantId))
 .ifPresent(_formLabelConstant -> formLabelConstantTranslation.setFormLabelConstant(_formLabelConstant.get()));

 CommonUtils.optionalChaining(()-> formLabelConstantTranslation.getLanguage().getLanguageId())
 .map(_languageId -> languageService.findById(_languageId))
 .ifPresent(_language -> formLabelConstantTranslation.setLanguage(_language.get()));

 return formLabelConstantTranslation;

 }

}
