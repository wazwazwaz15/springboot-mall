package com.bowei.springbootmall.service;

import com.bowei.springbootmall.constant.ProductCategory;
import com.bowei.springbootmall.dto.ProductQueryParams;
import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.util.Page;

import java.util.List;

public interface ProductService {


    Product getProcductById(Integer productId);


    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProcducts(ProductQueryParams productQueryParams);

    Integer countProducts(ProductQueryParams params);
}
