

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
@RequestMapping("/ClientSideDetail")
@Slf4j
@Validated
@Tag(name = "ClientSideDetail_Controller")
public class ClientSideDetailController {

 @Autowired
 private ClientSideDetailService<ClientSideDetail> clientSideDetailService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<ClientSideDetail> findClientSideDetailById(Integer id) {
		ClientSideDetail clientSideDetail = clientSideDetailService.findById(id).get();
		return ResponseHandler.getResponse(clientSideDetail);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<ClientSideDetail>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<ClientSideDetail> clientSideDetail = clientSideDetailService.findWithPaging(page, size);
		return ResponseHandler.getResponse(clientSideDetail);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteClientSideDetail(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = clientSideDetailService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<ClientSideDetail>> createClientSideDetail(@RequestBody @Valid List<ClientSideDetailVo.SaveClientSideDetail> clientSideDetailVos) {
		List<ClientSideDetail> clientSideDetails = clientSideDetailVos.stream().map( clientSideDetailVo -> {
			ClientSideDetail clientSideDetail = CloneUtils.getClone(new ClientSideDetail(), clientSideDetailVo);
      			return clientSideDetail;
		}).collect(Collectors.toList());
		List<ClientSideDetail> response = clientSideDetailService.createMany(clientSideDetails);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<ClientSideDetail>> updateClientSideDetail(@RequestBody List<ClientSideDetailVo.UpdateClientSideDetail> clientSideDetailVos) {
		List<ClientSideDetail> clientSideDetails = clientSideDetailVos.stream().map( clientSideDetailVo -> {
			ClientSideDetail foundClientSideDetail = clientSideDetailService.findById(clientSideDetailVo.getClientSideDetailId()).get();
			ClientSideDetail clientSideDetail = CloneUtils.getClone(foundClientSideDetail, clientSideDetailVo);
      			return clientSideDetail;
		}).collect(Collectors.toList());

		List<ClientSideDetail> response = clientSideDetailService.updateMany(clientSideDetails);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<ClientSideDetail>> filterClientSideDetail(@RequestBody ClientSideDetailVo.PublicClientSideDetail clientSideDetailVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		ClientSideDetail clientSideDetail = ObjectMapperUtils.getObjectMapper().convertValue(clientSideDetailVo, new TypeReference<ClientSideDetail>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(clientSideDetail);
		log.info("queryMap: {}", queryMap);
		List<ClientSideDetail> clientSideDetailList = clientSideDetailService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(clientSideDetailList);
	}

}
