package com.demo.angular.controller;

import com.demo.angular.dto.SizeDTO;
import com.demo.angular.model.Size;
import com.demo.angular.model.TypeReport;
import com.demo.angular.service.TypeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class TypeReportController {

  @Autowired
  TypeReportService typeReportService;

  @GetMapping("/typeReport")
  public ResponseEntity<?> getListSize() {
    List<TypeReport> typeReports = typeReportService.getAllTypeReport();
    return ResponseEntity.ok(typeReports);
  }

}
