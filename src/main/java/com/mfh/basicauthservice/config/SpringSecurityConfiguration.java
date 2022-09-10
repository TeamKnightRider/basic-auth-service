package com.mfh.basicauthservice.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfiguration {

  //  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .antMatcher("/")
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .failureHandler(authenticationFailureHandler())
        .and()
        .httpBasic()
        .authenticationEntryPoint(authenticationEntryPoint());
    return http.build();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    //    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    return new CustomAuthenticationEntryPoint();
  }

  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new CustomAuthenticationFailureHandler();
  }

  //  @Bean
  //  public UserDetailsService userDetailsService() {
  //    //    UserDetails user =
  //    //        User.withDefaultPasswordEncoder()
  //    //            .username("user")
  //    //            .password("password")
  //    //            .roles("USER")
  //    //            .build();
  //    //    return new InMemoryUserDetailsManager(user);
  //    return userDetailsService;
  //  }

  @Bean
  public BCryptPasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
