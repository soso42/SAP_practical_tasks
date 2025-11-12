package com.example.productservice.service;

import com.example.productservice.dto.ProductCreateDTO;
import com.example.productservice.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO save(ProductCreateDTO createDto);
    ProductDTO getProductById(Integer id);
    List<ProductDTO> getAll();
}
