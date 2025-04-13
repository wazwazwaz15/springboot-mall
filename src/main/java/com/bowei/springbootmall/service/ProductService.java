package com.bowei.springbootmall.service;

import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;

public interface ProductService {


    Product getProcductById(Integer productId);


    Integer createProduct(ProductRequest productRequest);
}
