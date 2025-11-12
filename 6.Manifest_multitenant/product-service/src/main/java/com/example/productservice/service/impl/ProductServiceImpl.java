package com.example.productservice.service.impl;

import com.example.productservice.dto.ProductCreateDTO;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ModelMapper mapper;

    @Override
    public ProductDTO save(ProductCreateDTO createDto) {
        Product product = mapper.map(createDto, Product.class);
        product = repository.save(product);
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = repository.findAll();
        List<ProductDTO> result = new ArrayList<>();
        products.forEach(product -> result.add(mapper.map(product, ProductDTO.class)));
        return result;
    }

}
