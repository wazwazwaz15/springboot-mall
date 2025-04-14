package com.bowei.springbootmall.controller;


import com.bowei.springbootmall.constant.ProductCategory;
import com.bowei.springbootmall.dto.ProductQueryParams;
import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.service.ProductService;
import com.bowei.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@Validated
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/products")                           //查詢條件-Filtering
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
                                                     @RequestParam(required = false) String search ,
                                                     // 排序 Sorting
                                                     @RequestParam(defaultValue = "created_date") String orderBy,
                                                     @RequestParam(defaultValue = "desc") String sort,
                                                     // 分頁 Pagination
                                                      @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit ,
                                                      @RequestParam (defaultValue = "0") @Min(0)  Integer offset) {

        ProductQueryParams params = new ProductQueryParams();
        params.setCategory(category);
        params.setSearch(search);
        params.setOrderBy(orderBy);
        params.setSort(sort);
        params.setLimit(limit);
        params.setOffset(offset);

        List<Product> productList = service.getProcducts(params);


        Integer total = service.countProducts(params);

        Page<Product> productPage = new Page<>();
        productPage.setTotal(total);
        productPage.setLimit(limit);
        productPage.setOffset(offset);
        productPage.setResults(productList);


        return ResponseEntity.status(HttpStatus.OK).body(productPage);
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
