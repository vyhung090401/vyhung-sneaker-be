package com.demo.angular.dto;

import com.demo.angular.model.HistoryReport;
import com.demo.angular.model.InventoryProductSize;
import com.demo.angular.model.Order;
import com.demo.angular.model.ReportDetail;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetailDTO implements Serializable {

  public ReportDetailDTO(ReportDetail reportDetail){
    this.id = reportDetail.getId();
    this.sizeNumber = reportDetail.getInventoryProductSize().getProductSize().getSize().getSizeNumber();
    this.historyReportId = reportDetail.getHistoryReport().getId();
    this.quantity = reportDetail.getQuantity();
    this.purchasePrice = reportDetail.getPurchasePrice();
    this.retailPrice = reportDetail.getRetailPrice();
  }

  private Long id;
  private Integer sizeNumber;
  private Long historyReportId;
  private Integer quantity;
  private Double purchasePrice;
  private Double retailPrice;

}
