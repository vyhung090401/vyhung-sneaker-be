package com.demo.angular.repository;

import com.demo.angular.model.TypeReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeReportRepository extends JpaRepository<TypeReport, Long> {

  List<TypeReport> findAll();
}
