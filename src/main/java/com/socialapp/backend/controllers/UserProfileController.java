package com.socialapp.backend.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialapp.backend.model.Users;
import com.socialapp.backend.response.ResponseHandler;
import com.socialapp.backend.service.UsersService;

@RestController
@RequestMapping("/api/v1/user")
public class UserProfileController {

	@Autowired
	private UsersService usersService;

	@GetMapping("/get-all-users")
	public ResponseEntity<Object> getAllUsers(@RequestParam(name= "page") int page) {
		return ResponseHandler.responseHanler("Users List Got Sucessfully.", HttpStatus.OK,usersService.getAllUsers(page));
	}



	@GetMapping("/page")
	ResponseEntity<Object> page(@RequestParam(name= "page") int page) {
		return ResponseHandler.responseHanler("Users List Got Sucessfully.", HttpStatus.OK,usersService.getAllUsers(page));
	}

}
