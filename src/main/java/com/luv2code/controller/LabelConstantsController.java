

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
@RequestMapping("/LabelConstants")
@Slf4j
@Validated
@Tag(name = "LabelConstants_Controller")
public class LabelConstantsController {

 @Autowired
 private LabelConstantsService<LabelConstants> labelConstantsService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<LabelConstants> findLabelConstantsById(Integer id) {
		LabelConstants labelConstants = labelConstantsService.findById(id).get();
		return ResponseHandler.getResponse(labelConstants);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<LabelConstants>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<LabelConstants> labelConstants = labelConstantsService.findWithPaging(page, size);
		return ResponseHandler.getResponse(labelConstants);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteLabelConstants(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = labelConstantsService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<LabelConstants>> createLabelConstants(@RequestBody @Valid List<LabelConstantsVo.SaveLabelConstants> labelConstantsVos) {
		List<LabelConstants> labelConstantss = labelConstantsVos.stream().map( labelConstantsVo -> {
			LabelConstants labelConstants = CloneUtils.getClone(new LabelConstants(), labelConstantsVo);
      			return labelConstants;
		}).collect(Collectors.toList());
		List<LabelConstants> response = labelConstantsService.createMany(labelConstantss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<LabelConstants>> updateLabelConstants(@RequestBody List<LabelConstantsVo.UpdateLabelConstants> labelConstantsVos) {
		List<LabelConstants> labelConstantss = labelConstantsVos.stream().map( labelConstantsVo -> {
			LabelConstants foundLabelConstants = labelConstantsService.findById(labelConstantsVo.getLabelConstantsId()).get();
			LabelConstants labelConstants = CloneUtils.getClone(foundLabelConstants, labelConstantsVo);
      			return labelConstants;
		}).collect(Collectors.toList());

		List<LabelConstants> response = labelConstantsService.updateMany(labelConstantss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<LabelConstants>> filterLabelConstants(@RequestBody LabelConstantsVo.PublicLabelConstants labelConstantsVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		LabelConstants labelConstants = ObjectMapperUtils.getObjectMapper().convertValue(labelConstantsVo, new TypeReference<LabelConstants>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(labelConstants);
		log.info("queryMap: {}", queryMap);
		List<LabelConstants> labelConstantsList = labelConstantsService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(labelConstantsList);
	}

}
