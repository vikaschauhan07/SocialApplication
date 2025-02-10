package com.socialapp.backend.model;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 55, nullable = true)
	private String name;

	@Column(name = "user_name", length = 55, unique = true, nullable = false)
	private String userName;

	@Column(name = "email", length = 125, unique = true, nullable = false)
	private String email;

	@Column(name = "email_verified", nullable = false)
	private int emailVerified;

	@Column(name = "password", nullable = true)
	private String password;

	@Column(name = "phone_code", length = 10, nullable = true)
	private String phoneCode;

	@Column(name = "iso_code", length = 10, nullable = true)
	private String isoCode;

	@Column(name = "phone_number", length = 15, nullable = true)
	private String phoneNumber;

	@Column(name = "profile_image", nullable = true)
	private String profileImage;

	@Column(name = "profile_cover_image", nullable = true)
	private String profileCoverImage;

	@Column(name = "theme", nullable = true)
	private String theme;

	@Column(name = "dob", nullable = true)
	private Timestamp dob;

	@Column(name = "location", nullable = true)
	private String location;

	@Column(name = "latitude", nullable = true)
	private String latitude;

	@Column(name = "longitude", nullable = true)
	private String longitude;

	@Column(name = "language", nullable = false)
	private String language;

	@Column(name = "hobbies", nullable = true)
	private String hobbies;

	@Column(name = "links", nullable = true)
	private String links;

	@Column(name = "bio", nullable = true)
	private String bio;

	@Column(name = "gender", nullable = true)
	private Short gender;

	@Column(name = "private_account", nullable = false)
	private Short privateAccount;

	@Column(name = "is_verified", nullable = false)
	private Integer isVerified;

	@Column(name = "notification_status", nullable = false)
	private Short notificationStatus;

	@Column(name = "role", nullable = false)
	private Integer role;

	@Column(name = "online_status", nullable = false)
	private Integer onlineStatus;

	@Column(name = "socket_id", nullable = true)
	private String socketId;

	@Column(name = "status", nullable = false)
	private Integer status;

	@Column(name = "terms_condition", nullable = false)
	private Boolean termsCondition;

	@Column(name = "otp", nullable = true)
	private String otp;

	@Column(name = "fcm_token", nullable = true)
	private String fcmToken;

	@Column(name = "device_type", nullable = false)
	private Short deviceType;

	@Column(name = "can_share_story", nullable = false)
	private Short canShareStory;

	@Column(name = "remember_token", nullable = true)
	private String rememberToken;

	@Column(name = "created_at", nullable = true)
	private Timestamp createdAt;

	@Column(name = "updated_at", nullable = true)
	private Timestamp updatedAt;

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
