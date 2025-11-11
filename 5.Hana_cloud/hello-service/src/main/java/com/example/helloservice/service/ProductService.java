package com.example.helloservice.service;

import com.example.helloservice.dto.ProductDTO;
import com.example.helloservice.vdm.namespaces.productsrv.Product;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(Long id);
    List<ProductDTO> getTopProducts(int top);
}
