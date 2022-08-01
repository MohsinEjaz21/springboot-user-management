package com.luv2code.exception;

import com.luv2code.utils.Constants;

import lombok.experimental.UtilityClass;

public class NoSuchElementFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
	public static void ENTITY_NOT_FOUNT(Integer id) {
		throw new RuntimeException(Constants.ENTITY_NOT_FOUND_EXCEPTION + id);
	}

  public void INVALID_ENTITY(String msg) {
		throw new RuntimeException(Constants.INVALID_ENTITY);
  }
  

}

