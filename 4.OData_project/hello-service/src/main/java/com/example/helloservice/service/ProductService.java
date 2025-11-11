package com.example.helloservice.service;

import com.example.helloservice.vdm.namespaces.productsrv.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    List<Product> getTopProducts(int top);
}
