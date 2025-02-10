package com.socialapp.backend.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	public static ResponseEntity<Object> responseHanler(String message, HttpStatus httpStatus,Object object){
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("status_code", httpStatus);
		response.put("data", object);
		return new ResponseEntity(response,httpStatus);
	}

}
