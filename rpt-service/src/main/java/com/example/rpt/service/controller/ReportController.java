package com.example.rpt.service.controller;

import com.example.ext.service.model.dto.RoomDto;
import com.example.rpt.service.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ReportController {

  @Autowired private ReportService reportService;

  @GetMapping("/test-dubbo/{id}")
  public RoomDto testDubbo(@PathVariable Long id) {
    return reportService.testDubbo(id);
  }
}
