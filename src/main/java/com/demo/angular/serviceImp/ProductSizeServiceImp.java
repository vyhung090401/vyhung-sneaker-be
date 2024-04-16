package com.demo.angular.serviceImp;
import com.demo.angular.dto.ProductSizeDTO;
import com.demo.angular.dto.SizeDTO;
import com.demo.angular.model.ProductSize;
import com.demo.angular.repository.ProductSizeRepository;
import com.demo.angular.repository.SizeRepository;
import com.demo.angular.service.ProductSizeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductSizeServiceImp implements ProductSizeService {

    @Autowired
    ProductSizeRepository productSizeRepository;
    @Autowired
    SizeRepository sizeRepository;

    @Override
    public List<ProductSizeDTO> getProductSizeByProductId(Long productId){
        List<ProductSizeDTO> productSizeDTOList = new ArrayList<>();
        List<ProductSize> productSizes = productSizeRepository.findAllByProductId(productId);
        for (ProductSize productSize : productSizes){
            ProductSizeDTO productSizeDTO = new ProductSizeDTO(productSize);
            SizeDTO sizeDTO = new SizeDTO(sizeRepository.findById(productSize.getSize().getId()).get());
            productSizeDTO.setSizeDTO(sizeDTO);
            productSizeDTOList.add(productSizeDTO);
        }
        return productSizeDTOList;
    }
}
