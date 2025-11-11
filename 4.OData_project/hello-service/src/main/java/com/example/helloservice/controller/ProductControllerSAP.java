package com.example.helloservice.controller;

import com.example.helloservice.service.impl.ProductServiceImplSAP;
import com.example.helloservice.vdm.namespaces.productsrv.Product;
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
@RequestMapping("sap/products")
@Slf4j
@RequiredArgsConstructor
public class ProductControllerSAP {

    private final ProductServiceImplSAP service;


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.debug("Started getting product by id: {}", id);
        Product product = service.getProductById(id);

        log.debug("Finished retrieving product by id: {}", id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getTopProducts() {
        log.debug("Started getting top products");
        List<Product> products = service.getTopProducts(10);

        log.debug("Finished retrieving list of products}");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
