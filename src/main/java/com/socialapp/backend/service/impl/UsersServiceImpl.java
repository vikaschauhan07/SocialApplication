package com.socialapp.backend.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialapp.backend.model.Users;
import com.socialapp.backend.model.dto.UsersDto;
import com.socialapp.backend.repository.UsersRepository;
import com.socialapp.backend.requests.UserAuthentiacateRequest;
import com.socialapp.backend.requests.UsersRegisterRequest;
import com.socialapp.backend.response.ResponseHandler;
import com.socialapp.backend.response.UserAuthenticationResponse;
import com.socialapp.backend.response.UserRegisterResponse;
import com.socialapp.backend.service.EmailSendService;
import com.socialapp.backend.service.FileSaveService;
import com.socialapp.backend.service.JwtService;
import com.socialapp.backend.service.UsersService;
import org.springframework.data.domain.PageImpl;

import jakarta.transaction.Transactional;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private FileSaveService fileSaveService;

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private EmailSendService emailSendService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Value("${project.image}")
	private String filePath;

	@Value("${project.url}")
	private String projectUrl;

	@Override
	@Transactional
	public UserRegisterResponse register(UsersRegisterRequest userRegisterRequest) throws IOException {

		Optional<Users> optionalUser = userRepository.findByEmail(userRegisterRequest.getEmail());
		
		String profileImage = fileSaveService.saveFile(projectUrl, filePath, userRegisterRequest.getProfile_image());

		String userName = userRegisterRequest.getName() + (Math.random() * 100001);
		String otp = "1234";

		var userData = Users.builder().name(userRegisterRequest.getName()).email(userRegisterRequest.getEmail())
				.password(passwordEncoder.encode(userRegisterRequest.getPassword()))
				.phoneCode(userRegisterRequest.getPhone_code()).phoneNumber(userRegisterRequest.getPhone_number())
				.fcmToken(userRegisterRequest.getFcm_token()).dob(userRegisterRequest.getDob())
				.termsCondition(userRegisterRequest.getTerms_condition()).profileImage(profileImage).userName(userName)
				.emailVerified(0).build();

		if (optionalUser.isPresent()) {
//			userRepository.deleteById(optionalUser.get().getId());
//			userRepository.flush();
			userData.setId(optionalUser.get().getId());
		}

		Users registeredUser = userRepository.save(userData);
		
		emailSendService.sendMail(registeredUser.getEmail(), "Verification Otp", otp);

		return UserRegisterResponse.builder().id(registeredUser.getId()).name(registeredUser.getName())
				.email(registeredUser.getEmail()).user_name(registeredUser.getUsername())
				.profile_image(projectUrl + registeredUser.getProfileImage()).build();

	}

	@Override
	@Transactional
	public UserAuthenticationResponse authenticate(UserAuthentiacateRequest userAuthentiacateRequest) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthentiacateRequest.getEmail(),
				userAuthentiacateRequest.getPassword()));

		Users user = userRepository.findByEmail(userAuthentiacateRequest.getEmail()).orElseThrow();
		UsersDto userDto = UsersDto.builder()
				.id(user.getId())
				.email(user.getEmail())
				.name(user.getName())
				.build();
		var accessToken = jwtService.generateToken(user);

		return UserAuthenticationResponse.builder().access_token(accessToken).user(userDto).build();

	}

	@Override
	@Transactional
	public Optional<Users> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public void sendMailToUser(String subject, String body, Users user) {
		String otp = "1111";
		userRepository.updatePhoneOtpColumn(user.getId(), otp);
		emailSendService.sendMail(user.getEmail(), subject, otp);
	}

	@Override
	@Transactional
	public Users getUserById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	@Transactional
	public Page<UsersDto> getAllUsers(int pageNo) {

		Pageable pageable = PageRequest.of(pageNo, 2);
		Page<Users> usersPage = userRepository.findAll(pageable);

		List<UsersDto> userList = userRepository.findAll(pageable).getContent().stream()
//				.filter((users) -> users.getEmailVerified() != 0)
				.map(users -> UsersDto.builder().name(users.getName()).email(users.getEmail()).build())
				.collect(Collectors.toList());
		System.out.println("Hello");
		return new PageImpl(userList, pageable, userList.size());
//		new PageImpl(u, pageable, usersPage.getTotalElements())

	}

	@Override
	public String genrateRandomToken(Users user) {
		String token = "abcdefghijklmnopqrstuvwxyz";
		user.setRememberToken("abcdefghijklmnopqrstuvwxyz");
		userRepository.save(user);
		return token;
		
	}

	@Override
	public void updatePassword(Users user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

}
