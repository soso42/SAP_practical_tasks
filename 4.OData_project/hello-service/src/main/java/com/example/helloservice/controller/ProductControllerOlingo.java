package com.example.helloservice.controller;

import com.example.helloservice.dto.ProductDTO;
import com.example.helloservice.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "olingo/products")
@Slf4j
@RequiredArgsConstructor
public class ProductControllerOlingo {

    private final ProductServiceImpl productService;


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> getTopProducts() {
        List<ProductDTO> products = productService.getTopProducts(10);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
