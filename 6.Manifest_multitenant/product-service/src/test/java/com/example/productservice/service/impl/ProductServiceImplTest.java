package com.example.productservice.service.impl;

import com.example.productservice.dto.ProductCreateDTO;
import com.example.productservice.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class ProductServiceImplTest {

//    @Autowired
//    private ProductServiceImpl service;
//
//    @Test
//    void save() {
//        // Given
//        ProductCreateDTO dto = new ProductCreateDTO("MacBook Air", 999);
//
//        // When
//        ProductDTO result = service.save(dto);
//
//        // Then
//        assertNotNull(result);
//        assertNotNull(result.getId());
//        assertEquals(result.getName(), dto.getName());
//        assertEquals(result.getPrice(), dto.getPrice());
//    }
//
//    @Test
//    void findById() {
//        // Given
//        ProductCreateDTO dto = new ProductCreateDTO("HP Laptop", 999);
//        ProductDTO hpLaptop = service.save(dto);
//        Integer hpId = hpLaptop.getId();
//
//        // When
//        ProductDTO result = service.getProductById(hpId);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(result.getId(), hpId);
//        assertEquals(result.getName(), hpLaptop.getName());
//        assertEquals(result.getPrice(), hpLaptop.getPrice());
//    }
//
//    @Test
//    void findAll() {
//        // Given
//        ProductCreateDTO dto = new ProductCreateDTO("HP Laptop", 999);
//        ProductDTO hpLaptop = service.save(dto);
//        Integer hpId = hpLaptop.getId();
//
//        // When
//        List<ProductDTO> result = service.getAll();
//
//        // Then
//        assertTrue(result.size() > 0);
//    }
}