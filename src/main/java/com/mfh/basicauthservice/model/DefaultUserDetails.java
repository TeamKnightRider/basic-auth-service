package com.mfh.basicauthservice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mfh.commonmodel.user.User;
import com.mfh.commonmodel.user.account.Account;

@Slf4j
@NoArgsConstructor
public class DefaultUserDetails implements UserDetails {

  private User user;
  private Account account;

  public DefaultUserDetails(User user) {
    this.user = user;
    if (Objects.nonNull(user)) {
      this.account = user.getAccount();
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (Objects.isNull(user)) {
      throw new IllegalArgumentException("User not found!");
    }
    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
    user.getRoles()
        .forEach(role -> role.getAuthorities()
            .forEach(authority -> grantedAuthorityList.add(new SimpleGrantedAuthority(authority.getName()))));
    //    return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    return grantedAuthorityList;
  }

  @Override
  public String getPassword() {
    if (Objects.isNull(account)) {
      throw new IllegalArgumentException("Account not found!");
    }
    return String.valueOf(account.getPassword());
  }

  @Override
  public String getUsername() {
    if (Objects.isNull(account)) {
      throw new IllegalArgumentException("Account not found!");
    }
    return account.getAccountName();
  }

  @Override
  public boolean isAccountNonExpired() {
    if (Objects.isNull(account)) {
      throw new IllegalArgumentException("Account not found!");
    }
    return !account.isExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    if (Objects.isNull(account)) {
      throw new IllegalArgumentException("Account not found!");
    }
    return !account.isLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    if (Objects.isNull(account)) {
      throw new IllegalArgumentException("Account not found!");
    }
    return !account.isCredentialExpired();
  }

  @Override
  public boolean isEnabled() {
    if (Objects.isNull(user)) {
      throw new IllegalArgumentException("User not found!");
    }
    return user.isActive() && user.getAccount()
        .isActive();
  }

  public User getUser() {
    return user;
  }
}
