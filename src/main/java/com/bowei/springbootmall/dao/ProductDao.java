package com.bowei.springbootmall.dao;

import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductId(Integer productId);

    List<Product> getProducts();
}
