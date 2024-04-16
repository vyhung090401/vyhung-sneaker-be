package com.demo.angular.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "report_detail")
public class ReportDetail extends Auditor{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "inventory_product_size_id")
  private InventoryProductSize inventoryProductSize;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(targetEntity = HistoryReport.class)
  @JoinColumn(name = "history_report_id", nullable = false)
  private HistoryReport historyReport;

  @Column(name="quantity")
  private Integer quantity;

  //giá nhập kho
  @Column(name="purchase_price")
  private Double purchasePrice;

  //giá bán lẻ
  @Column(name="retail_price")
  private Double retailPrice;

}
