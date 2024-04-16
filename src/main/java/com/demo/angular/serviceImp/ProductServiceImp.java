package com.demo.angular.serviceImp;

import com.demo.angular.dto.*;
import com.demo.angular.exception.ResourceNotFoundException;
import com.demo.angular.model.*;
import com.demo.angular.repository.*;
import com.demo.angular.repository.specification.ProductSpecification;
import com.demo.angular.service.InventoryProductService;
import com.demo.angular.service.ProductService;
import com.demo.angular.service.ProductSizeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImp implements ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ImageRepository imageRepository;
  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private ProductSizeRepository productSizeRepository;
  @Autowired
  private ProductSizeService productSizeService;
  @Autowired
  private InventoryProductService inventoryProductService;
  @Autowired
  private InventoryRepository inventoryRepository;
  @Autowired
  private InventoryProductRepository inventoryProductRepository;
  @Autowired
  private InventoryProductSizeRepository inventoryProductSizeRepository;

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Transactional
  @Override
  public ProductDTO createProduct(ProductDTO productDTO, InventoryProductDTO inventoryProductDTO) {
    List<Size> sizes = sizeRepository.findAll();
    List<ProductSize> productSizes = new ArrayList<>();
    Product product = new Product();
    product.setName(productDTO.getName());
    product.setPrice(inventoryProductDTO.getPurchasePrice());
    product.setColor(productDTO.getColor());
    product.setBrand(productDTO.getBrand());
    product.setStatus(productDTO.getStatus());
    productRepository.save(product);

    for (int i = 0; i < sizes.size(); i++) {
      ProductSize ps = new ProductSize();
      InventoryProductSize inventoryProductSize = new InventoryProductSize();
      ps.setProduct(product); // product vừa tạo
      ps.setSize(sizes.get(i));
      ps.setQuantity(0);
      ps.setStatus(EStatusProduct.Available);
      productSizeRepository.save(ps);
      productSizes.add(ps); // Thêm vào list
      inventoryProductSize.setQuantity(0);
      inventoryProductSize.setInventory(inventoryRepository.findByName(inventoryProductDTO.getInventoryName()));
      inventoryProductSize.setProduct(product);
      inventoryProductSize.setProductSize(ps);
      inventoryProductSizeRepository.save(inventoryProductSize);
    }
    product.setProductSizes(productSizes);
    productRepository.save(product);
    List<Image> images = new ArrayList<>();
    List<ImageDTO> imageDTOList = productDTO.getImages();
    for (ImageDTO imageDTO : imageDTOList) {
      Image image = new Image();
      image.setType(imageDTO.getType());
      image.setPicByte(imageDTO.getPicByte());
      image.setLink(imageDTO.getLink());
      image.setProduct(product);
      imageRepository.save(image);
      images.add(image);
    }
    product.setImages(images);
    productRepository.save(product);
    InventoryProduct inventoryProduct = new InventoryProduct();
    inventoryProduct.setProduct(product);
    inventoryProduct.setQuantity(0);
    inventoryProduct.setRetailPrice(0.0);
    inventoryProduct.setPurchasePrice(0.0);
    inventoryProduct.setInventory(inventoryRepository.findByName(inventoryProductDTO.getInventoryName()));
    inventoryProductRepository.save(inventoryProduct);
    ProductDTO newProductDTO = new ProductDTO(product);
    return newProductDTO;
  }


  @Override
  public Product getProduct(Long id) {
    return productRepository.findById(id).get();
  }

  @Override
  public Product updateProduct(ProductDTO productDTO) {
    Product product = productRepository.findById(productDTO.getId()).get();
    List<ImageDTO> imagesRequestUpdate = productDTO.getImages();
    product.setName(productDTO.getName());
    product.setPrice(productDTO.getPrice());
    product.setStatus(productDTO.getStatus());
    List<ProductSizeDTO> productSizeDTOS = productDTO.getProductSizes();
    List<ProductSize> productSizes = product.getProductSizes();
    for (int i = 0; i < productSizeDTOS.size(); i++) {
      productSizes.get(i).setQuantity(productSizeDTOS.get(i).getQuantity());
    }
    product.setColor(productDTO.getColor());
    product.setBrand(productDTO.getBrand());
    imageRepository.deleteByProductId(product.getId());
    List<Image> images = new ArrayList<>();
    for (ImageDTO imageDTO : imagesRequestUpdate) {
      Image image = new Image();
      image.setType(imageDTO.getType());
      image.setPicByte(imageDTO.getPicByte());
      image.setLink(imageDTO.getLink());
      image.setProduct(product);
      imageRepository.save(image);
      images.add(image);
    }
    product.setImages(images);
    return productRepository.save(product);
  }

  @Override
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

  @Override
  public List<Product> getProductsByPriceRange(Double upperBound) {
    return productRepository.getProductsByPriceRange(upperBound);
  }

  @Override
  public List<ProductDTO> getProductsByPriceLessThanAndPriceGreaterThan(Double maxPrice, Double minPrice) {
    List<Product> products = productRepository.findByPriceLessThanAndPriceGreaterThan(maxPrice, minPrice);
    List<ProductDTO> productDTOList = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO(product);
      productDTOList.add(productDTO);
    }
    return productDTOList;
  }

  @Override
  public List<Product> getProductsByPriceGreaterThan(Double minPrice) {
    return productRepository.findByPriceGreaterThan(minPrice);
  }

  @Override
  public List<Product> getProductsByBrandAndPriceGreaterThan(String brand, Double upperBound) {
    return productRepository.getProductsByBrandAndPriceGreaterThan(brand, upperBound);
  }

  @Override
  public List<Product> getProductsByBrandAndPriceLessThan(String brand, Double upperBound) {
    return productRepository.getProductsByBrandAndPriceLessThan(brand, upperBound);
  }

  @Override
  public List<Product> searchProductsByName(String keyword) {
    // Sử dụng repository để tìm kiếm sản phẩm theo từ khóa
    return productRepository.findByKeywordByName(keyword);
  }


  @Override
  public List<Product> searchProductsByBrand(String keyword) {
    // Sử dụng repository để tìm kiếm sản phẩm theo từ khóa
    return productRepository.findByKeywordByBrand(keyword);
  }

  @Override
  public List<Product> searchProductsByColor(String keyword) {
    // Sử dụng repository để tìm kiếm sản phẩm theo từ khóa
    return productRepository.findByKeywordByColor(keyword);
  }

  @Override
  public Page<ProductDTO> getProducts(int page, int size) {
    Page<Product> productPage = productRepository.findAllByQuantityGreaterThan(0, PageRequest.of(page, size));
    List<Product> products = productPage.getContent();
    List<ProductDTO> productDTOS = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO();
      for (Image img : product.getImages()) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(img.getId());
        imageDTO.setType(img.getType());
        imageDTO.setLink(img.getLink());
        imageDTO.setPicByte(img.getPicByte());
        productDTO.getImages().add(imageDTO);
      }
      productDTO.setId(product.getId());
      productDTO.setName(product.getName());
      productDTO.setColor(product.getColor());
      productDTO.setBrand(product.getBrand());
      productDTO.setPrice(product.getPrice());
      productDTO.setStatus(product.getStatus());
      productDTO.setQuantity(product.getQuantity());

      productDTOS.add(productDTO);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOS, pageable, productPage.getTotalElements());
    return productDTOPage;
  }

  @Override
  public ProductDTO getProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product is not exist with id: " + id));
    ProductDTO productDTO = new ProductDTO(product);
    List<ProductSize> productSizes = productSizeRepository.findAllByProductId(id);
    List<ProductSizeDTO> productSizeDTOS = new ArrayList<>();
    for (ProductSize productSize : productSizes) {
      ProductSizeDTO productSizeDTO = new ProductSizeDTO(productSize);
      SizeDTO sizeDTO = new SizeDTO(sizeRepository.findById(productSize.getSize().getId()).get());
      productSizeDTO.setSizeDTO(sizeDTO);
      productSizeDTOS.add(productSizeDTO);
    }
    productDTO.setProductSizes(productSizeDTOS);
    List<Image> images = product.getImages();
    List<ImageDTO> imageDTOS = new ArrayList<>();
    for (Image image : images) {
      ImageDTO imageDTO = new ImageDTO(image);
      imageDTOS.add(imageDTO);
    }
    productDTO.setImages(imageDTOS);
    return productDTO;
  }

  @Override
  public Page<ProductDTO> searchProductsByKeyword(String keyword, int page, int size) {
    Page<Product> productPage = productRepository.findByKeyword(keyword, PageRequest.of(page, size));
    List<Product> products = productPage.getContent();
    List<ProductDTO> productDTOList = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO(product);
      for (Image image : product.getImages()) {
        ImageDTO imageDTO = new ImageDTO(image);
        productDTO.getImages().add(imageDTO);
      }
      productDTOList.add(productDTO);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOList, pageable, productPage.getTotalElements());
    return productDTOPage;
  }

  @Override
  public Page<ProductDTO> getAvailableProducts(int page, int size) {
    Page<Product> productPage = productRepository.findAllAvailableProducts(PageRequest.of(page, size));
    List<Product> products = productPage.getContent();
    List<ProductDTO> productDTOS = new ArrayList<>();

    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO();
      for (Image img : product.getImages()) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(img.getId());
        imageDTO.setType(img.getType());
        imageDTO.setLink(img.getLink());
        imageDTO.setPicByte(img.getPicByte());
        productDTO.getImages().add(imageDTO);
      }
      productDTO.setId(product.getId());
      productDTO.setName(product.getName());
      productDTO.setColor(product.getColor());
      productDTO.setBrand(product.getBrand());
      productDTO.setPrice(product.getPrice());
      productDTO.setStatus(product.getStatus());
      productDTOS.add(productDTO);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOS, pageable, productPage.getTotalElements());
    return productDTOPage;
  }

  @Override
  public Page<ProductDTO> findProductsByFilter(List<List<Double>> priceRanges,
                                               List<String> brandList,
                                               int page,
                                               int size) {
    Specification conditions = Specification.where(ProductSpecification.filterByPriceRanges(priceRanges)
        .and(ProductSpecification.filterByBrand(brandList)));
    Page<Product> products = productRepository.findAll(
        conditions, PageRequest.of(page, size)
    );
    List<ProductDTO> productDTOList = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO(product);
      productDTOList.add(productDTO);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOList, pageable, products.getTotalElements());
    return productDTOPage;
  }

  @Override
  public Page<ProductDTO> findProductsByPriceRanges(List<List<Double>> priceRanges, int page, int size) {
    Page<Product> products = productRepository.findAll(
        ProductSpecification.filterByPriceRanges(priceRanges), PageRequest.of(page, size)
    );
    List<ProductDTO> productDTOList = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO(product);
      productDTOList.add(productDTO);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOList, pageable, products.getTotalElements());
    return productDTOPage;
  }

  @Override
  public Page<ProductDTO> findProductsByBrand(List<String> brandList, int page, int size) {
    Page<Product> products = productRepository.findAll(
        ProductSpecification.filterByBrand(brandList), PageRequest.of(page, size)
    );
    List<ProductDTO> productDTOList = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO(product);
      productDTOList.add(productDTO);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOList, pageable, products.getTotalElements());
    return productDTOPage;
  }

  @Override
  public Page<ProductDTO> findProductsByAdvanceFilter(Double minPrice,
                                                      Double maxPrice,
                                                      String inventoryName,
                                                      String productName,
                                                      List<String> brandList,
                                                      List<String> colorList,
                                                      List<EStatusProduct> statusList,
                                                      int page,
                                                      int size) {
    Page<Product> productPage = productRepository.findByFilter(inventoryName, productName, colorList, brandList,
        statusList, minPrice, maxPrice, PageRequest.of(page, size));
    List<Product> products = productPage.getContent();
    List<ProductDTO> productDTOS = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = new ProductDTO();
      for (Image img : product.getImages()) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(img.getId());
        imageDTO.setType(img.getType());
        imageDTO.setLink(img.getLink());
        imageDTO.setPicByte(img.getPicByte());
        productDTO.getImages().add(imageDTO);
      }
      productDTO.setId(product.getId());
      productDTO.setName(product.getName());
      productDTO.setColor(product.getColor());
      productDTO.setBrand(product.getBrand());
      productDTO.setPrice(product.getPrice());
      productDTO.setStatus(product.getStatus());
      productDTO.setQuantity(product.getQuantity());
      productDTOS.add(productDTO);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOS, pageable, productPage.getTotalElements());
    System.out.println(productDTOPage.getContent());
    return productDTOPage;

  }

}
