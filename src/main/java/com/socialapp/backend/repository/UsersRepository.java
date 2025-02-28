package com.socialapp.backend.repository;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import com.socialapp.backend.model.Users;

import jakarta.transaction.Transactional;

public interface UsersRepository extends JpaRepository<Users, Long>{
	 
	Optional<Users> findByEmail(String email);
	
//	Page<Users> findAll(Pageable pageable);
	
	@Modifying
	@Query("update Users u set u.otp = :otp where u.id = :id")
	void updatePhoneOtpColumn(@Param(value = "id") long id, @Param(value = "otp") String otp);
	
//	void deleteUserById()
//	Page<Users> findAll(pageable);
	@Query("SELECT u FROM Users u WHERE u.emailVerified = :emailVerified")
	Page<Users> findVerifiedUsers(@Param("emailVerified") int emailVerified, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("UPDATE Users u SET u.emailVerified = :emailVerified WHERE u.id = :userId")
	int verifyUserEmail(@Param("userId") Long userId,@Param("emailVerified") int emailVerified);

}
