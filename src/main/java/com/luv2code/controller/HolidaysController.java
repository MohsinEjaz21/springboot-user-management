

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
@RequestMapping("/Holidays")
@Slf4j
@Validated
@Tag(name = "Holidays_Controller")
public class HolidaysController {

 @Autowired
 private ProjectService<Project> projectService;
 @Autowired
 private ClientService<Client> clientService;

 @Autowired
 private HolidaysService<Holidays> holidaysService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Holidays> findHolidaysById(Integer id) {
		Holidays holidays = holidaysService.findById(id).get();
		return ResponseHandler.getResponse(holidays);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Holidays>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Holidays> holidays = holidaysService.findWithPaging(page, size);
		return ResponseHandler.getResponse(holidays);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteHolidays(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = holidaysService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Holidays>> createHolidays(@RequestBody @Valid List<HolidaysVo.SaveHolidays> holidaysVos) {
		List<Holidays> holidayss = holidaysVos.stream().map( holidaysVo -> {
			Holidays holidays = CloneUtils.getClone(new Holidays(), holidaysVo);
      			setFks(holidays);
      			return holidays;
		}).collect(Collectors.toList());
		List<Holidays> response = holidaysService.createMany(holidayss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Holidays>> updateHolidays(@RequestBody List<HolidaysVo.UpdateHolidays> holidaysVos) {
		List<Holidays> holidayss = holidaysVos.stream().map( holidaysVo -> {
			Holidays foundHolidays = holidaysService.findById(holidaysVo.getHolidaysId()).get();
			Holidays holidays = CloneUtils.getClone(foundHolidays, holidaysVo);
      			setFks(holidays);
      			return holidays;
		}).collect(Collectors.toList());

		List<Holidays> response = holidaysService.updateMany(holidayss);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Holidays>> filterHolidays(@RequestBody HolidaysVo.PublicHolidays holidaysVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Holidays holidays = ObjectMapperUtils.getObjectMapper().convertValue(holidaysVo, new TypeReference<Holidays>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(holidays);
		log.info("queryMap: {}", queryMap);
		List<Holidays> holidaysList = holidaysService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(holidaysList);
	}

 private Holidays setFks(Holidays holidays) {

 CommonUtils.optionalChaining(()-> holidays.getProject().getProjectId())
 .map(_projectId -> projectService.findById(_projectId))
 .ifPresent(_project -> holidays.setProject(_project.get()));

 CommonUtils.optionalChaining(()-> holidays.getClient().getClientId())
 .map(_clientId -> clientService.findById(_clientId))
 .ifPresent(_client -> holidays.setClient(_client.get()));

 return holidays;

 }

}
