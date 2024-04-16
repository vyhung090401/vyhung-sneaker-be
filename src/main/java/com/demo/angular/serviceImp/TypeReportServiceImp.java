package com.demo.angular.serviceImp;

import com.demo.angular.model.TypeReport;
import com.demo.angular.repository.TypeReportRepository;
import com.demo.angular.service.TypeReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TypeReportServiceImp implements TypeReportService {

  @Autowired
  TypeReportRepository typeReportRepository;

  public List<TypeReport> getAllTypeReport(){
    List<TypeReport> typeReports = typeReportRepository.findAll();
    return typeReports;
  }

}
