

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
@RequestMapping("/ControlShortCutKey")
@Slf4j
@Validated
@Tag(name = "ControlShortCutKey_Controller")
public class ControlShortCutKeyController {

 @Autowired
 private ControlShortCutKeyService<ControlShortCutKey> controlShortCutKeyService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<ControlShortCutKey> findControlShortCutKeyById(Integer id) {
		ControlShortCutKey controlShortCutKey = controlShortCutKeyService.findById(id).get();
		return ResponseHandler.getResponse(controlShortCutKey);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<ControlShortCutKey>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<ControlShortCutKey> controlShortCutKey = controlShortCutKeyService.findWithPaging(page, size);
		return ResponseHandler.getResponse(controlShortCutKey);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteControlShortCutKey(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = controlShortCutKeyService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<ControlShortCutKey>> createControlShortCutKey(@RequestBody @Valid List<ControlShortCutKeyVo.SaveControlShortCutKey> controlShortCutKeyVos) {
		List<ControlShortCutKey> controlShortCutKeys = controlShortCutKeyVos.stream().map( controlShortCutKeyVo -> {
			ControlShortCutKey controlShortCutKey = CloneUtils.getClone(new ControlShortCutKey(), controlShortCutKeyVo);
      			return controlShortCutKey;
		}).collect(Collectors.toList());
		List<ControlShortCutKey> response = controlShortCutKeyService.createMany(controlShortCutKeys);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<ControlShortCutKey>> updateControlShortCutKey(@RequestBody List<ControlShortCutKeyVo.UpdateControlShortCutKey> controlShortCutKeyVos) {
		List<ControlShortCutKey> controlShortCutKeys = controlShortCutKeyVos.stream().map( controlShortCutKeyVo -> {
			ControlShortCutKey foundControlShortCutKey = controlShortCutKeyService.findById(controlShortCutKeyVo.getControlShortCutKeyId()).get();
			ControlShortCutKey controlShortCutKey = CloneUtils.getClone(foundControlShortCutKey, controlShortCutKeyVo);
      			return controlShortCutKey;
		}).collect(Collectors.toList());

		List<ControlShortCutKey> response = controlShortCutKeyService.updateMany(controlShortCutKeys);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<ControlShortCutKey>> filterControlShortCutKey(@RequestBody ControlShortCutKeyVo.PublicControlShortCutKey controlShortCutKeyVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		ControlShortCutKey controlShortCutKey = ObjectMapperUtils.getObjectMapper().convertValue(controlShortCutKeyVo, new TypeReference<ControlShortCutKey>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(controlShortCutKey);
		log.info("queryMap: {}", queryMap);
		List<ControlShortCutKey> controlShortCutKeyList = controlShortCutKeyService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(controlShortCutKeyList);
	}

}
