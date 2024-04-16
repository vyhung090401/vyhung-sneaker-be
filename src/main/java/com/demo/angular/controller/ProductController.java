package com.demo.angular.controller;

import com.demo.angular.dto.ImageDTO;
import com.demo.angular.dto.InventoryProductDTO;
import com.demo.angular.dto.ProductDTO;
import com.demo.angular.exception.ResourceNotFoundException;
import com.demo.angular.model.EStatusProduct;
import com.demo.angular.model.Image;
import com.demo.angular.model.Product;
import com.demo.angular.repository.ImageRepository;
import com.demo.angular.repository.ProductRepository;
import com.demo.angular.service.ProductService;
import com.demo.angular.serviceImp.ProductServiceImp;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/api/v1/")

public class ProductController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private ProductService productService;

    //get all products
    @GetMapping("/products")
    public ResponseEntity<?>getProductsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "5") int size) {
        try {
            Page<ProductDTO> products = productServiceImp.getProducts(page, size);
            return ResponseEntity.ok(products);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    //create product
    @PostMapping(value = "/products",consumes ={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> CreateProducts(@RequestPart("product") ProductDTO productDTO,
                                            @RequestPart("inventoryProduct") InventoryProductDTO inventoryProductDTO,
                                            @RequestPart("imageFile")MultipartFile[] files) {

        ProductDTO productDTO1;
        try {
            List<ImageDTO> imageDTOList = uploadImage(files);
            productDTO.setImages(imageDTOList);
            productDTO1 = productService.createProduct(productDTO, inventoryProductDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return ResponseEntity.ok(productDTO1);
    }
    public List<ImageDTO> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        List<ImageDTO> imageDTOList = new ArrayList<>();
        for(MultipartFile file : multipartFiles){
            Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            ImageDTO imageDTO = new ImageDTO(image);
            imageDTOList.add(imageDTO);
        }
        return imageDTOList;
    }

    //get product by ID
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        ProductDTO productDTO= productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    //update product by ID
    @PutMapping(value = "/products",consumes ={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProductById(@RequestPart("product")ProductDTO productRequestUpdate
                                               ,@RequestPart("imageFile")MultipartFile[] files) {

        try {
            List<ImageDTO> images = uploadImage(files);
            productRequestUpdate.setImages(images);
            productService.updateProduct(productRequestUpdate);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return ResponseEntity.ok(productRequestUpdate);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable Long id){
        Product product= productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not exist with id: "+id));
        productRepository.delete(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products/search/{keyword}")
    public ResponseEntity<?> searchProduct(@PathVariable String keyword,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size){
        Page<ProductDTO> productDTOPage = productServiceImp.searchProductsByKeyword(keyword,page,size);
        return ResponseEntity.ok(productDTOPage);
    }

    //get all products with amount > 0
    @GetMapping("/products/available")
    public ResponseEntity<?> getAvailableProductsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        try {
            Page<ProductDTO> products = productServiceImp.getAvailableProducts(page, size);
            return ResponseEntity.ok(products);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/products/brand")
    public ResponseEntity<?> getBrandList(){
        List<String> brandList = productRepository.getAllBrands();
        return ResponseEntity.ok(brandList);
    }

    @GetMapping("/products/color")
    public ResponseEntity<?> getColorList(){
        List<String> colorList = productRepository.getAllColors();
        return ResponseEntity.ok(colorList);
    }

    @GetMapping("/products/findByBrand")
    public ResponseEntity<?> getProductListByBrand(@RequestParam List<String> brandList,
                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "size", defaultValue = "5") int size){
        Page<ProductDTO> products = productService.findProductsByBrand(brandList,page,size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/findByFilter")
    public ResponseEntity<?> getProductListByFilter(@RequestParam List<List<Double>> priceRange,
                                                    @RequestParam List<String> brandList,
                                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "size", defaultValue = "5") int size){
        System.out.println(priceRange);
        System.out.println(brandList);
        Page<ProductDTO> productDTOPage = productService.findProductsByFilter(priceRange,brandList,page,size);
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/products/findByPrice")
    public ResponseEntity<?> getProductListByPriceRanges(@RequestParam List<List<Double>> priceRange,
                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "size", defaultValue = "5") int size){
        Page<ProductDTO> products = productService.findProductsByPriceRanges(priceRange,page,size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/findByAdvanceFilter")
    public ResponseEntity<?> getProductListByAdvanceFilter(@RequestParam(required = false) Double maxPrice,
                                                    @RequestParam(required = false) Double minPrice,
                                                    @RequestParam(required = false)  String inventoryName,
                                                    @RequestParam(required = false) String productName,
                                                    @RequestParam(required = false) List<String> brandList,
                                                    @RequestParam(required = false) List<String> colorList,
                                                    @RequestParam(required = false) List<EStatusProduct> statusList,
                                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "size", defaultValue = "5") int size){
        System.out.println(maxPrice);
        System.out.println(minPrice);
        System.out.println(inventoryName);
        System.out.println(productName);
        System.out.println(brandList);
        System.out.println(colorList);
        System.out.println(statusList);

        Page<ProductDTO> productDTOPage = productService.findProductsByAdvanceFilter(minPrice,maxPrice,inventoryName,productName,brandList,colorList,statusList,page,size);
        return ResponseEntity.ok(productDTOPage);
    }


}
