

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
@RequestMapping("/WorkingDays")
@Slf4j
@Validated
@Tag(name = "WorkingDays_Controller")
public class WorkingDaysController {

 @Autowired
 private ProjectService<Project> projectService;
 @Autowired
 private ClientService<Client> clientService;

 @Autowired
 private WorkingDaysService<WorkingDays> workingDaysService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<WorkingDays> findWorkingDaysById(Integer id) {
		WorkingDays workingDays = workingDaysService.findById(id).get();
		return ResponseHandler.getResponse(workingDays);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<WorkingDays>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<WorkingDays> workingDays = workingDaysService.findWithPaging(page, size);
		return ResponseHandler.getResponse(workingDays);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteWorkingDays(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = workingDaysService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<WorkingDays>> createWorkingDays(@RequestBody @Valid List<WorkingDaysVo.SaveWorkingDays> workingDaysVos) {
		List<WorkingDays> workingDayss = workingDaysVos.stream().map( workingDaysVo -> {
			WorkingDays workingDays = CloneUtils.getClone(new WorkingDays(), workingDaysVo);
      			setFks(workingDays);
      			return workingDays;
		}).collect(Collectors.toList());
		List<WorkingDays> response = workingDaysService.createMany(workingDayss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<WorkingDays>> updateWorkingDays(@RequestBody List<WorkingDaysVo.UpdateWorkingDays> workingDaysVos) {
		List<WorkingDays> workingDayss = workingDaysVos.stream().map( workingDaysVo -> {
			WorkingDays foundWorkingDays = workingDaysService.findById(workingDaysVo.getWorkingDaysId()).get();
			WorkingDays workingDays = CloneUtils.getClone(foundWorkingDays, workingDaysVo);
      			setFks(workingDays);
      			return workingDays;
		}).collect(Collectors.toList());

		List<WorkingDays> response = workingDaysService.updateMany(workingDayss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<WorkingDays>> filterWorkingDays(@RequestBody WorkingDaysVo.PublicWorkingDays workingDaysVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		WorkingDays workingDays = ObjectMapperUtils.getObjectMapper().convertValue(workingDaysVo, new TypeReference<WorkingDays>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(workingDays);
		log.info("queryMap: {}", queryMap);
		List<WorkingDays> workingDaysList = workingDaysService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(workingDaysList);
	}

 private WorkingDays setFks(WorkingDays workingDays) {

 CommonUtils.optionalChaining(()-> workingDays.getProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> workingDays.setProject(_project.get()));

 CommonUtils.optionalChaining(()-> workingDays.getClient().getClientId())
 .map(_clientId -> clientService.findById(_clientId))
 .ifPresent(_client -> workingDays.setClient(_client.get()));

 return workingDays;

 }

}
