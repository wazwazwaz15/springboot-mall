package com.bowei.springbootmall.service;

import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;

public interface ProductService {


    Product getProcductById(Integer productId);


    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
