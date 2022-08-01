

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
@RequestMapping("/Language")
@Slf4j
@Validated
@Tag(name = "Language_Controller")
public class LanguageController {

 @Autowired
 private LanguageService<Language> languageService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Language> findLanguageById(Integer id) {
		Language language = languageService.findById(id).get();
		return ResponseHandler.getResponse(language);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Language>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Language> language = languageService.findWithPaging(page, size);
		return ResponseHandler.getResponse(language);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteLanguage(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = languageService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Language>> createLanguage(@RequestBody @Valid List<LanguageVo.SaveLanguage> languageVos) {
		List<Language> languages = languageVos.stream().map( languageVo -> {
			Language language = CloneUtils.getClone(new Language(), languageVo);
      			return language;
		}).collect(Collectors.toList());
		List<Language> response = languageService.createMany(languages);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Language>> updateLanguage(@RequestBody List<LanguageVo.UpdateLanguage> languageVos) {
		List<Language> languages = languageVos.stream().map( languageVo -> {
			Language foundLanguage = languageService.findById(languageVo.getLanguageId()).get();
			Language language = CloneUtils.getClone(foundLanguage, languageVo);
      			return language;
		}).collect(Collectors.toList());

		List<Language> response = languageService.updateMany(languages);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Language>> filterLanguage(@RequestBody LanguageVo.PublicLanguage languageVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Language language = ObjectMapperUtils.getObjectMapper().convertValue(languageVo, new TypeReference<Language>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(language);
		log.info("queryMap: {}", queryMap);
		List<Language> languageList = languageService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(languageList);
	}

}
