package com.mfh.basicauthservice.repository;

import java.util.Optional;

import com.mfh.commonmodel.user.User;

public interface CustomUserRepository {

  User findByUsername(String userName);

  Optional<User> findByRid(String rid);
}
