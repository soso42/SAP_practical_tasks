package com.example.helloservice.service;

import com.example.helloservice.dto.ProductCreateDTO;
import com.example.helloservice.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductCreateDTO dto);
}
