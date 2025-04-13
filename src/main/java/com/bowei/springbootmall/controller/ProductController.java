package com.bowei.springbootmall.controller;


import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> productList = service.getProcducts();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {

        Product product = service.getProcductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = service.createProduct(productRequest);

        Product product = service.getProcductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        Product product = service.getProcductById(productId);

        if (product == null) {
            log.error("productId:" + productId + "不存在");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        service.updateProduct(productId, productRequest);

        Product updatedProduct = service.getProcductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        service.deleteProductById(productId);


        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
