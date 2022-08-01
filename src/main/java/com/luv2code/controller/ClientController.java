

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
@RequestMapping("/Client")
@Slf4j
@Validated
@Tag(name = "Client_Controller")
public class ClientController {

 @Autowired
 private ClientService<Client> clientService;

	@GetMapping(value = "/findOne/{id}")
	public ResponseModel<Client> findClientById(Integer id) {
		Client client = clientService.findById(id).get();
		return ResponseHandler.getResponse(client);
	}

	@GetMapping(value = "/findAll/{page}/{size}")
	public ResponseModel<List<Client>> findWithPaging(
	    @RequestParam(required = false, defaultValue = "-1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {
		List<Client> client = clientService.findWithPaging(page, size);
		return ResponseHandler.getResponse(client);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseModel<Boolean> deleteClient(
			@RequestParam(required = true, defaultValue = "1", value = "id") int id) {
		Boolean isDeleted = clientService.deleteById(id);
		return ResponseHandler.getResponse(isDeleted);
	}

	@PostMapping(value = "/create")
	public ResponseModel<List<Client>> createClient(@RequestBody @Valid List<ClientVo.SaveClient> clientVos) {
		List<Client> clients = clientVos.stream().map( clientVo -> {
			Client client = CloneUtils.getClone(new Client(), clientVo);
      			return client;
		}).collect(Collectors.toList());
		List<Client> response = clientService.createMany(clients);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/update")
	public ResponseModel<List<Client>> updateClient(@RequestBody List<ClientVo.UpdateClient> clientVos) {
		List<Client> clients = clientVos.stream().map( clientVo -> {
			Client foundClient = clientService.findById(clientVo.getClientId()).get();
			Client client = CloneUtils.getClone(foundClient, clientVo);
      			return client;
		}).collect(Collectors.toList());

		List<Client> response = clientService.updateMany(clients);
		return ResponseHandler.getResponse(response);
	}

	@PostMapping(value = "/filter/{page}/{size}")
	public ResponseModel<List<Client>> filterClient(@RequestBody ClientVo.PublicClient clientVo,
	    @RequestParam(required = false, defaultValue = "1", value = "page") int page,
	    @RequestParam(required = false, defaultValue = "10", value = "size") int size) {

		Client client = ObjectMapperUtils.getObjectMapper().convertValue(clientVo, new TypeReference<Client>() {});
		HashMap<String, java.lang.Object> queryMap = QueryUtils.getInstance().getQueryMap(client);
		log.info("queryMap: {}", queryMap);
		List<Client> clientList = clientService.filter(queryMap, page, size);
		return ResponseHandler.getResponse(clientList);
	}

}
