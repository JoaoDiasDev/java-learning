package com.joaodiasdev.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firstroute")
public class MyFirstController {

  @GetMapping("/hello")
  public String firstMessage() {
    return "Hello World!";
  }
}