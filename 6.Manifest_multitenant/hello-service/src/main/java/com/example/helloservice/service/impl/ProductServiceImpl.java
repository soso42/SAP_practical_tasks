package com.example.helloservice.service.impl;

import com.example.helloservice.dto.ProductCreateDTO;
import com.example.helloservice.dto.ProductDTO;
import com.example.helloservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {



    @Override
    public ProductDTO getProductById(Long id) {
        return null;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductDTO createProduct(ProductCreateDTO dto) {
        return null;
    }

}
