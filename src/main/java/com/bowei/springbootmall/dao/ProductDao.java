package com.bowei.springbootmall.dao;

import com.bowei.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
