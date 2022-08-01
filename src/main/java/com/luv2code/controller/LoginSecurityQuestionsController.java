

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
@RequestMapping("/LoginSecurityQuestions")
@Slf4j
@Validated
@Tag(name = "LoginSecurityQuestions_Controller")
public class LoginSecurityQuestionsController {

 @Autowired
 private LoginSecurityQuestionsService<LoginSecurityQuestions> loginSecurityQuestionsService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<LoginSecurityQuestions> findLoginSecurityQuestionsById(Integer id) {
		LoginSecurityQuestions loginSecurityQuestions = loginSecurityQuestionsService.findById(id).get();
		return ResponseHandler.getResponse(loginSecurityQuestions);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<LoginSecurityQuestions>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<LoginSecurityQuestions> loginSecurityQuestions = loginSecurityQuestionsService.findWithPaging(page, size);
		return ResponseHandler.getResponse(loginSecurityQuestions);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteLoginSecurityQuestions(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = loginSecurityQuestionsService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<LoginSecurityQuestions>> createLoginSecurityQuestions(@RequestBody @Valid List<LoginSecurityQuestionsVo.SaveLoginSecurityQuestions> loginSecurityQuestionsVos) {
		List<LoginSecurityQuestions> loginSecurityQuestionss = loginSecurityQuestionsVos.stream().map( loginSecurityQuestionsVo -> {
			LoginSecurityQuestions loginSecurityQuestions = CloneUtils.getClone(new LoginSecurityQuestions(), loginSecurityQuestionsVo);
      			return loginSecurityQuestions;
		}).collect(Collectors.toList());
		List<LoginSecurityQuestions> response = loginSecurityQuestionsService.createMany(loginSecurityQuestionss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<LoginSecurityQuestions>> updateLoginSecurityQuestions(@RequestBody List<LoginSecurityQuestionsVo.UpdateLoginSecurityQuestions> loginSecurityQuestionsVos) {
		List<LoginSecurityQuestions> loginSecurityQuestionss = loginSecurityQuestionsVos.stream().map( loginSecurityQuestionsVo -> {
			LoginSecurityQuestions foundLoginSecurityQuestions = loginSecurityQuestionsService.findById(loginSecurityQuestionsVo.getLoginSecurityQuestionsId()).get();
			LoginSecurityQuestions loginSecurityQuestions = CloneUtils.getClone(foundLoginSecurityQuestions, loginSecurityQuestionsVo);
      			return loginSecurityQuestions;
		}).collect(Collectors.toList());

		List<LoginSecurityQuestions> response = loginSecurityQuestionsService.updateMany(loginSecurityQuestionss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<LoginSecurityQuestions>> filterLoginSecurityQuestions(@RequestBody LoginSecurityQuestionsVo.PublicLoginSecurityQuestions loginSecurityQuestionsVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		LoginSecurityQuestions loginSecurityQuestions = ObjectMapperUtils.getObjectMapper().convertValue(loginSecurityQuestionsVo, new TypeReference<LoginSecurityQuestions>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(loginSecurityQuestions);
		log.info("queryMap: {}", queryMap);
		List<LoginSecurityQuestions> loginSecurityQuestionsList = loginSecurityQuestionsService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(loginSecurityQuestionsList);
	}

}
