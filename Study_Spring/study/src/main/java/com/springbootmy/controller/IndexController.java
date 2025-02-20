package com.springbootmy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("message", "Hello Spring Boot!");
    model.addAttribute("checked", true);
    return "index";
  }
  
}
