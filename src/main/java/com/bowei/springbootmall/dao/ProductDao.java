package com.bowei.springbootmall.dao;

import com.bowei.springbootmall.constant.ProductCategory;
import com.bowei.springbootmall.dto.ProductQueryParams;
import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.util.Page;

import java.util.List;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductId(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Integer countProducts(ProductQueryParams params);

    void updateStock(Integer productId, int restOfStock);
}
