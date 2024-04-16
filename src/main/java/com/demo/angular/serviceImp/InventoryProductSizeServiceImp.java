package com.demo.angular.serviceImp;

import com.demo.angular.dto.InventoryProductSizeDTO;
import com.demo.angular.model.*;
import com.demo.angular.payload.request.ExportReceiptForm;
import com.demo.angular.repository.*;
import com.demo.angular.service.InventoryProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryProductSizeServiceImp implements InventoryProductSizeService {

    @Autowired
    InventoryProductSizeRepository inventoryProductSizeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSizeRepository productSizeRepository;

    @Autowired
    InventoryProductRepository inventoryProductRepository;

    @Autowired
    HistoryReportRepository historyReportRepository;

    public List<InventoryProductSizeDTO> getInventoryProductSizeList(Long productId){

        List<InventoryProductSize> inventoryProductSizes = inventoryProductSizeRepository.findAllByProductSizeProductId(productId);
        List<InventoryProductSizeDTO> inventoryProductSizeDTOS = new ArrayList<>();
        for(InventoryProductSize inventoryProductSize: inventoryProductSizes){
            InventoryProductSizeDTO inventoryProductSizeDTO = new InventoryProductSizeDTO(inventoryProductSize);
            inventoryProductSizeDTOS.add(inventoryProductSizeDTO);
        }
        return inventoryProductSizeDTOS;
    }

    public void exportInventoryProduct(ExportReceiptForm exportReceiptForm){

        List<InventoryProductSize> inventoryProductSizes = inventoryProductSizeRepository.findAllByProductId(exportReceiptForm.getId());
        List<ProductSize> productSizes = productSizeRepository.findAllByProductId(exportReceiptForm.getId());
        HistoryReport historyReport = new HistoryReport();
        historyReport.setTypeReport(exportReceiptForm.getTypeReport());
        historyReportRepository.save(historyReport);
        List<ReportDetail> reportDetails = new ArrayList<>();
        for (int i = 0;i < inventoryProductSizes.size(); i++){
            inventoryProductSizes.get(i).setQuantity(inventoryProductSizes.get(i).getQuantity() - exportReceiptForm.getExportInventoryProductsize().get(i).getQuantity());
            productSizes.get(i).setQuantity(productSizes.get(i).getQuantity() + exportReceiptForm.getExportInventoryProductsize().get(i).getQuantity());
            inventoryProductSizeRepository.save(inventoryProductSizes.get(i));
            productSizeRepository.save(productSizes.get(i));
            ReportDetail reportDetail = new ReportDetail();
            if (exportReceiptForm.getExportInventoryProductsize().get(i).getQuantity() != 0)
            reportDetail.setInventoryProductSize(inventoryProductSizes.get(i));
            reportDetail.setQuantity(exportReceiptForm.getExportInventoryProductsize().get(i).getQuantity());
            reportDetail.setHistoryReport(historyReport);
            reportDetail.setRetailPrice(exportReceiptForm.getSellPrice());
            reportDetail.setPurchasePrice(exportReceiptForm.getCostPrice());
            reportDetails.add(reportDetail);
        }
        historyReport.setReportDetails(reportDetails);
        historyReportRepository.save(historyReport);

    }

    public void receiptInventoryProduct(ExportReceiptForm exportReceiptForm){

        Integer totalInventoryQty = 0;

        List<InventoryProductSize> inventoryProductSizes = inventoryProductSizeRepository.findAllByProductId(exportReceiptForm.getId());
        InventoryProduct inventoryProduct = inventoryProductRepository.findByProductId(exportReceiptForm.getId());
        inventoryProduct.setPurchasePrice(exportReceiptForm.getCostPrice());
        inventoryProduct.setRetailPrice(exportReceiptForm.getSellPrice());
        Product product =productRepository.findById(exportReceiptForm.getId()).get();
        product.setPrice(exportReceiptForm.getSellPrice());

        for (int i = 0;i < inventoryProductSizes.size(); i++){
            inventoryProductSizes.get(i).setQuantity(inventoryProductSizes.get(i).getQuantity() + exportReceiptForm.getExportInventoryProductsize().get(i).getQuantity());
            totalInventoryQty += exportReceiptForm.getExportInventoryProductsize().get(i).getQuantity();
            inventoryProductSizeRepository.save(inventoryProductSizes.get(i));
        }
        inventoryProduct.setQuantity(totalInventoryQty);
        inventoryProductRepository.save(inventoryProduct);
        productRepository.save(product);
    }

}
