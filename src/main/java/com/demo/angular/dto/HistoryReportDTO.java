package com.demo.angular.dto;

import com.demo.angular.model.HistoryReport;
import com.demo.angular.model.TypeReport;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryReportDTO implements Serializable {

  public HistoryReportDTO(HistoryReport historyReport) {
    this.id = historyReport.getId();
    this.typeReportName = historyReport.getTypeReport().getName();
    this.account = historyReport.getCreatedBy();
    this.createDate = historyReport.getCreatedOn();
  }

  private Long id;
  private String typeReportName;
  private String account;
  private LocalDateTime createDate;
}
