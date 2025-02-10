package com.socialapp.backend.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
	
	private Long id;
	private String remember_token;
	private String password;
	private String confirm_password;
	

}
