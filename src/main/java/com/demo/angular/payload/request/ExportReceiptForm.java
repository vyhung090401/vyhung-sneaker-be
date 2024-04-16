package com.demo.angular.payload.request;

import com.demo.angular.dto.InventoryProductSizeDTO;
import com.demo.angular.model.TypeReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExportReceiptForm {
    Long id;
    List<InventoryProductSizeDTO> exportInventoryProductsize;
    Double costPrice;
    Double sellPrice;
    TypeReport typeReport;
}
