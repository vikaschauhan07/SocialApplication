package com.socialapp.backend.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {
	
	private Long id;
	private String name;
	private String user_name;
	private String email;
	private String profile_image;
	private Integer status;

}
