package com.bowei.springbootmall.service.imple;

import com.bowei.springbootmall.dao.ProductDao;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductDao dao;


    @Override
    public Product getProcductById(Integer productId) {
        return dao.getProductById(productId);
    }
}
