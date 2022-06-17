package com.example.ext.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ExportController {

  @GetMapping("/export")
  public String testHello() {
    return "Hello";
  }
}
