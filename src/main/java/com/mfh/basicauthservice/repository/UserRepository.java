package com.mfh.basicauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfh.commonmodel.user.User;

public interface UserRepository extends JpaRepository<User, String>, CustomUserRepository {
  //    @Query("SELECT u FROM User u WHERE u.userName = :userName")
  //    public User getUserByUsername(@Param("userName") String userName);
}
