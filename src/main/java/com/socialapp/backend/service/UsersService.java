package com.socialapp.backend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.socialapp.backend.model.Users;
import com.socialapp.backend.model.dto.UsersDto;
import com.socialapp.backend.requests.UserAuthentiacateRequest;
import com.socialapp.backend.requests.UsersRegisterRequest;
import com.socialapp.backend.response.UserAuthenticationResponse;
import com.socialapp.backend.response.UserRegisterResponse;

import java.io.IOException;
import java.util.List;
public interface UsersService {
	UserRegisterResponse register(UsersRegisterRequest userRegisterRequest) throws IOException;
	Optional<Users>getUserByEmail(String email);
	Users getUserById(Long id);
	UserAuthenticationResponse authenticate(UserAuthentiacateRequest userAuthentiacateRequest);
	void sendMailToUser(String subject,String body,Users user);
	Page<UsersDto> getAllUsers(int pageable);
	String genrateRandomToken(Users user);
	void updatePassword(Users user,String password);
//	ResponseEntity<Object> getAllUsers(int pageable);
//	List<UsersDto> getAllUsers(int pageable);

	
}
