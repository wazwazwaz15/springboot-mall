package com.bowei.springbootmall.service.imple;

import com.bowei.springbootmall.constant.ProductCategory;
import com.bowei.springbootmall.dao.ProductDao;
import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductDao dao;


    @Override
    public Product getProcductById(Integer productId) {
        return dao.getProductById(productId);
    }

    @Override
    @Transactional
    public Integer createProduct(ProductRequest productRequest) {
        return dao.createProduct(productRequest);
    }

    @Override
    @Transactional
    public void updateProduct(Integer productId, ProductRequest productRequest) {
         dao.updateProduct(productId, productRequest);
    }

    @Override
    @Transactional
    public void deleteProductById(Integer productId) {
        dao.deleteProductId(productId);
    }

    @Override
    public List<Product> getProcducts(ProductCategory category , String search) {
        List<Product> productList = dao.getProducts(category , search);
        return productList;
    }
}
