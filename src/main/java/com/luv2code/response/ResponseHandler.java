package com.luv2code.response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseHandler{

  public static Map<String, Object> generateResponse(String message, HttpStatus status, Object responseObj) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("message", message);
    map.put("status", status.value());
    map.put("data", responseObj);
    return map;
  }


  public static <E> ResponseModel<E> getResponse(E responseObj) {
    List<Boolean> failedResponse  = new ArrayList<Boolean>();
    failedResponse.add(responseObj == null);
    failedResponse.add(responseObj instanceof Collection && ((Collection)responseObj).isEmpty());
    failedResponse.add(responseObj instanceof Map && ((Map)responseObj).isEmpty());
    failedResponse.add(responseObj instanceof String && responseObj.equals(""));
    failedResponse.add(responseObj instanceof Boolean && responseObj.equals(false));

    if(failedResponse.contains(true)) {
     return new ResponseModel<E>("NO CONTENT", HttpStatus.NOT_FOUND, null);
    }
    return new ResponseModel<E>("SUCCESS", HttpStatus.OK, responseObj);
  }


  public static Map<String, Object> error(String message, String url, HttpStatus status, Throwable throwable) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("message", message);
    map.put("status", status);
    map.put("debugMessage", throwable);
    map.put("url", url);
    return map;
  }

}
