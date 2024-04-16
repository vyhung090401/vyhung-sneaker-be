package com.demo.angular.repository;

import com.demo.angular.model.Cart;
import com.demo.angular.model.HistoryReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryReportRepository extends JpaRepository<HistoryReport,Long> {

}
