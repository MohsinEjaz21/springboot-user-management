package com.luv2code.exception;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.persistence.PersistenceException;

import org.hibernate.PropertyValueException;
import org.hibernate.QueryException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
	    HttpStatus status, WebRequest request) {
		ExceptionResponseModel<String> errorResponse = new ExceptionResponseModel<String>(HttpStatus.UNPROCESSABLE_ENTITY.value(),
		    "Validation error. Check 'errors' field for details.");
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.unprocessableEntity().body(errorResponse);
	}

	@ExceptionHandler(NoSuchElementFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException itemNotFoundException,
	    WebRequest request) {
		return buildErrorResponse(itemNotFoundException, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleSQLException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
		printStackTrace(ex);
		return buildErrorResponse(ex, ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegealArgumentException(IllegalArgumentException ex, PropertyValueException pv, WebRequest request) {
		printStackTrace(ex);
		return buildErrorResponse(ex, ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}


	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<Object> handlePersistenceSQLException(PropertyValueException ex,
	    WebRequest request) {
		printStackTrace(ex);
		return buildErrorResponse(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(QueryException.class)
	public ResponseEntity<Object> handleQueryException(QueryException exception, WebRequest request) {
		printStackTrace(exception);
		return buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(java.lang.IllegalStateException.class)
	public ResponseEntity<Object> handleTransientObjectException(org.hibernate.TransientObjectException exception,
	    WebRequest request) {
		printStackTrace(exception);
		return buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(ConstraintViolationException ex,
	    WebRequest request) {
		printStackTrace(ex);
		return buildErrorResponse(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(PersistenceException.class)
	public ResponseEntity<Object> handlePersistenceException(PersistenceException ex,
	    WebRequest request) {
		printStackTrace(ex);
		String message ="PERSISTENCE EXCEPTION OCCURRED";
		Exception exception = (PersistenceException) ex;


		if(exception instanceof PersistenceException) {
			exception = (PersistenceException) exception;
    	message = exception.getMessage();
      if(exception.getCause() instanceof ConstraintViolationException) {
      	exception = (ConstraintViolationException) exception.getCause();
      	message = exception.getMessage();
        if(exception.getCause() instanceof SQLIntegrityConstraintViolationException) {
        	exception = (SQLIntegrityConstraintViolationException) exception.getCause();
        	message = exception.getMessage();
        }
      }
			if(exception.getCause() instanceof GenericJDBCException) {
				exception = (GenericJDBCException) exception.getCause();
				message = exception.getMessage();
				if(exception.getCause() instanceof SQLException) {
					exception = (SQLException) exception.getCause();
					message = exception.getMessage();
				}
			}
    }

		return buildErrorResponse(ex, message, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
		printStackTrace(exception);
		String message=exception.getLocalizedMessage();
   if(exception instanceof ConstraintViolationException) {
     	exception = (ConstraintViolationException) exception;
    	message = exception.getMessage();
	   }
		return buildErrorResponse(exception, message, HttpStatus.BAD_REQUEST, request);
	}


	private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus, WebRequest request) {
		printStackTrace(exception);
		return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
	}

	private <T> ResponseEntity<Object> buildErrorResponse(Exception exception, T message, HttpStatus httpStatus,WebRequest request) {
		ExceptionResponseModel<T> errorResponse = new ExceptionResponseModel<T>(httpStatus.value(), message);
		return ResponseEntity.status(httpStatus).body(errorResponse);
	}

	private void printStackTrace(Exception exception) {
		exception.printStackTrace();
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
	    HttpStatus status, WebRequest request) {
		printStackTrace(ex);
		return buildErrorResponse(ex, ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}