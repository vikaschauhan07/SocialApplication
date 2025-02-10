package com.socialapp.backend.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialapp.backend.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	 
	Optional<Users> findByEmail(String email);
	
//	Page<Users> findAll(Pageable pageable);
	
	@Modifying
	@Query("update Users u set u.otp = :otp where u.id = :id")
	void updatePhoneOtpColumn(@Param(value = "id") long id, @Param(value = "otp") String otp);
	
//	void deleteUserById()

}
