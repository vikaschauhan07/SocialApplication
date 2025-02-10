package com.socialapp.backend.response;

import com.socialapp.backend.model.dto.UsersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthenticationResponse {

	private String access_token;
	private String refresh_token;
	private UsersDto user;

	public static UserAuthenticationResponse userAuthenticationResponse(String access_toekn, String refresh_token,
			String message, UsersDto user) {

		return UserAuthenticationResponse.builder().access_token(access_toekn).refresh_token(refresh_token).user(user).build();
	}

}
