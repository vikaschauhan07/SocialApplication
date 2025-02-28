package com.socialapp.backend.controllers;

import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialapp.backend.model.Users;
import com.socialapp.backend.model.dto.UsersDto;
import com.socialapp.backend.requests.ResetPasswordRequest;
import com.socialapp.backend.requests.UserAuthentiacateRequest;
import com.socialapp.backend.requests.UserEmailVerifyRequest;
import com.socialapp.backend.requests.UsersRegisterRequest;
import com.socialapp.backend.response.ResponseHandler;
import com.socialapp.backend.response.UserAuthenticationResponse;
import com.socialapp.backend.service.FileSaveService;
import com.socialapp.backend.service.JwtService;
import com.socialapp.backend.service.UsersService;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class UsersController {

	@Autowired
	private FileSaveService fileSaveService;

	@Autowired
	private UsersService userserService;

	@Autowired
	private JwtService jwtService;
	
//	private Ma

	@Value("${project.image}")
	private String filePath;

	@PostMapping("/register")
	public ResponseEntity<Object> register(@ModelAttribute UsersRegisterRequest registerRequest) throws IOException {
		try {
			Optional<Users> users = userserService.getUserByEmail(registerRequest.getEmail());

			if (!users.isEmpty() && users.get().getEmailVerified() == 1) {
				return ResponseHandler.responseHanler("User with this email already exists.", HttpStatus.BAD_REQUEST,
						null);
			}
			String message = "User Registered Successfully.Verify your mail by useing the otp we sent over your mail";
			return ResponseHandler.responseHanler(message, HttpStatus.OK, userserService.register(registerRequest));
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return ResponseHandler.responseHanler("Email Already Exists.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		} catch (UnexpectedRollbackException e) {
			return ResponseHandler.responseHanler("Database Query not completed", HttpStatus.CONFLICT, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.responseHanler("Servr Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}

	}

	@PostMapping("/authenticate")
	public ResponseEntity<Object> authenticate(@RequestBody UserAuthentiacateRequest userAuthentiacateRequest) {
		try {
			Optional<Users> user = userserService.getUserByEmail(userAuthentiacateRequest.getEmail());
			if (user.isEmpty()) {
				return ResponseHandler.responseHanler("User Not Found with this email", HttpStatus.NOT_FOUND, null);
			}

			if (user.get().getEmailVerified() != 1) {
				userserService.sendMailToUser("Verification Otp", "This is a Mail", user.get());
				return ResponseHandler.responseHanler("Your Email Is Not Verified. We have sent Opt Over Your Mail",
						HttpStatus.BAD_REQUEST, null);
			} else {
				return ResponseHandler.responseHanler("User Authenticated Successfully.", HttpStatus.OK,
						userserService.authenticate(userAuthentiacateRequest));
			}

		} catch (NoSuchElementException ex) {
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.NOT_FOUND, null);

		} catch (NullPointerException ex) {
			return ResponseHandler.responseHanler("Email Is Not Verified.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		} catch (BadCredentialsException ex) {
			return ResponseHandler.responseHanler("Bad Credentials", HttpStatus.UNAUTHORIZED, null);
		} catch (Exception ex) {
			return ResponseHandler.responseHanler("Server Error.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PostMapping("/verify-email")
	public ResponseEntity<Object> verifyEmailOtp(@RequestBody UserEmailVerifyRequest userEmailVerifyRequest) {
		try {
			Users user = userserService.getUserById(userEmailVerifyRequest.getId());

			if (user == null) {
				return ResponseHandler.responseHanler("User With this is not found.", HttpStatus.BAD_REQUEST, null);
			}
		
			if (user.getOtp().equals(userEmailVerifyRequest.getOtp().toString())) {
				userserService.verifyUserEmail(user);
				String accessToekn = jwtService.generateToken(user);
				UsersDto userDto = UsersDto.builder().email(user.getEmail()).name(user.getName()).build();
				return ResponseHandler.responseHanler("Email Verified Successfully", HttpStatus.OK,
						UserAuthenticationResponse.builder().access_token(accessToekn).user(userDto).build());
			} else {
				return ResponseHandler.responseHanler("Otp Not matched.", HttpStatus.BAD_REQUEST, null);
			}
		} catch (NoSuchElementException ex) {
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.NOT_FOUND, null);
		} catch (Exception e) {
			System.out.println(e.toString());
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.INTERNAL_SERVER_ERROR,
					null);
		}
	}

	@GetMapping("/resend-verification-otp")
	public ResponseEntity<Object> resendVerificationOtp(@RequestParam(name = "id") Long userId) {
		try {
			Users user = userserService.getUserById(userId);
			if (user == null) {
				return ResponseHandler.responseHanler("User With this id not found.", HttpStatus.BAD_REQUEST, null);
			}
			userserService.sendMailToUser("Email Verification Otp", "Email Verifiacation Opt", user);
			return ResponseHandler.responseHanler("Otp Sent Ovber Your Email", HttpStatus.OK, null);

		} catch (NoSuchElementException ex) {
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.NOT_FOUND, null);
		} catch (Exception e) {
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.INTERNAL_SERVER_ERROR,
					null);
		}
	}

	@GetMapping("/forget-password")
	public ResponseEntity<Object> forgetPassword(@RequestParam(value = "email") String email) {
		try {
			Optional<Users> userG = userserService.getUserByEmail(email);
			
			if (userG.isEmpty()) {
				return ResponseHandler.responseHanler("User Not Found with this email", HttpStatus.NOT_FOUND, null);
			}
			
			var user = userG.get();
			UsersDto userDto = UsersDto.builder()
					.id(user.getId())
					.email(user.getEmail())
					.name(user.getName())
					.build();
			userserService.sendMailToUser("Forget Password Otp", "This is a Mail Forget password otp", user);
			return ResponseHandler.responseHanler("Email Sent Over Your Mail", HttpStatus.OK, userDto);
		} catch (NoSuchElementException ex) {
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.NOT_FOUND, null);

		} catch (NullPointerException ex) {
			return ResponseHandler.responseHanler("Email Is Not Verified.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		} catch (BadCredentialsException ex) {
			return ResponseHandler.responseHanler("Bad Credentials", HttpStatus.UNAUTHORIZED, null);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return ResponseHandler.responseHanler("Server Error.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}
	
	@GetMapping("/resend-forget-password-otp")
	public ResponseEntity<Object> resendForgetOtp(@RequestParam(name = "id") Long userId) {
		try {
			Users user = userserService.getUserById(userId);
			if (user == null) {
				return ResponseHandler.responseHanler("User With this id not found.", HttpStatus.BAD_REQUEST, null);
			}
			userserService.sendMailToUser("Forget Password otp", "Email Verifiacation Opt", user);
			return ResponseHandler.responseHanler("Otp Sent Ovber Your Email", HttpStatus.OK, null);

		} catch (NoSuchElementException ex) {
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.NOT_FOUND, null);
		} catch (Exception e) {
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.INTERNAL_SERVER_ERROR,
					null);
		}
	}

	@PostMapping("/verify-forget-password-otp")
	public ResponseEntity<Object> verifyForgetPasswordOtp(@RequestParam(name = "id") Long userId,
			@RequestParam(name = "otp") Integer otp) {
		try {
			Users user = userserService.getUserById(userId);

			if (user == null) {
				return ResponseHandler.responseHanler("User With this is not found.", HttpStatus.BAD_REQUEST, null);
			}
			if (user.getOtp().equals(otp.toString())) {
				String rememberToken = userserService.genrateRandomToken(user);
				UsersDto userDto = UsersDto.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).build();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("remeber_token", rememberToken);
				map.put("user", userDto);
				return ResponseHandler.responseHanler("Email Verified Successfully", HttpStatus.OK,map);
			} else {
				return ResponseHandler.responseHanler("Otp Not matched.", HttpStatus.BAD_REQUEST, null);
			}
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.NOT_FOUND, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest reset) {
		try {
			Users user = userserService.getUserById(reset.getId());

			if (user == null) {
				return ResponseHandler.responseHanler("User With this is not found.", HttpStatus.BAD_REQUEST, null);
			}
			if(user.getRememberToken().equals(reset.getRemember_token())){
				userserService.updatePassword(user, reset.getConfirm_password());
				return ResponseHandler.responseHanler("Password Changed Successfully", HttpStatus.OK,null);
			}
			return ResponseHandler.responseHanler("Token Not matched", HttpStatus.BAD_REQUEST,null);
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.NOT_FOUND, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.responseHanler("User with the email not found.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}
}
