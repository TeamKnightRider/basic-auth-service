package com.mfh.basicauthservice.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
public class TestController {
  public String testSecurity() {
    return "Hello Secured Data!";
  }
}
