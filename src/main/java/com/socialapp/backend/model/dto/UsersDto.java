package com.socialapp.backend.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
	private Long id;
	private String name;
	private String userName;
	private String email;
	private String phoneCode;
	private String phoneNumber;
	private String profileImage;
}
