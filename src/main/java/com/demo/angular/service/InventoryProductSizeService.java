package com.demo.angular.service;

import com.demo.angular.dto.InventoryProductSizeDTO;
import com.demo.angular.payload.request.ExportReceiptForm;

import java.util.List;

public interface InventoryProductSizeService {

    List<InventoryProductSizeDTO> getInventoryProductSizeList(Long productId);

    void exportInventoryProduct(ExportReceiptForm exportForm);

    void receiptInventoryProduct(ExportReceiptForm exportForm);
}
