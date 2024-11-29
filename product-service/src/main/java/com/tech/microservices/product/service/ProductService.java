package com.tech.microservices.product.service;

import com.tech.microservices.product.dto.ProductRequest;
import com.tech.microservices.product.dto.ProductResponse;
import com.tech.microservices.product.model.Product;
import com.tech.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;



    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Product created succesfully");
       // return this.modelMapper.map(product, ProductResponse.class);
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice());
    }


    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream().map(product -> new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice())).toList();

    }
}
