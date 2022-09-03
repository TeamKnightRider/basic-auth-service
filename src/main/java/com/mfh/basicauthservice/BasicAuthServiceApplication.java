package com.mfh.basicauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = {"com.mfh.commonmodel", "com.mfh.basicauthservice"},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.mfh.commonmodel.config.*"))
public class BasicAuthServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(BasicAuthServiceApplication.class, args);
  }
}
