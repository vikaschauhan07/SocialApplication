package com.socialapp.backend.requests;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersRegisterRequest {
	
	@NotBlank(message = "First name is required")
	private String name;
	
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "Password must contain at least one letter, one digit, and one special character")
	private String password;
	
	private Timestamp dob;
	
	private String phone_code;
	
	private String phone_number;
	
	private String fcm_token;
	
	private Short device_type;
	
	private Boolean terms_condition;
    
	private MultipartFile profile_image;
	
}
