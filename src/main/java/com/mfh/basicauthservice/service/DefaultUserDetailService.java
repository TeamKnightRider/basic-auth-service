package com.mfh.basicauthservice.service;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfh.basicauthservice.model.DefaultUserDetails;
import com.mfh.basicauthservice.repository.UserRepository;
import com.mfh.commonmodel.user.User;

@Slf4j
@Service("default")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(final String userName) {
    User user = findByUserName(userName);
    if (Objects.isNull(user)) {
      throw new BadCredentialsException("User not found!");
    }
    return new DefaultUserDetails(user);
  }

  @Transactional(readOnly = true)
  public User findByUserName(String userName) {
    return userRepository.findByUsername(userName);
  }

  protected boolean hasRole(String role) {
    // get security context from thread local
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null) {
      return false;
    }

    Authentication authentication = context.getAuthentication();
    if (authentication == null) {
      return false;
    }

    for (GrantedAuthority auth : authentication.getAuthorities()) {
      if (role.equals(auth.getAuthority())) {
        return true;
      }
    }

    return false;
  }

  //  public boolean isAuthenticated(String path) {
  //    return false;
  //  }
}
