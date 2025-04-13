package com.bowei.springbootmall.dao.imple;


import com.bowei.springbootmall.dao.ProductDao;
import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.model.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {
    private final String new_line = System.getProperty("line.separator");

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT product_id, product_name , category, image_url, price, stock, description, created_date, last_modified_date").append(new_line);
        sql.append("FROM mall.product").append(new_line);
        sql.append("WHERE product_id = :product_id;");

        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);

        List<Product> productList = jdbcTemplate.query(sql.toString(), map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }


    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO mall.product (product_name , category, image_url, price, stock, description, created_date, last_modified_date)").append(new_line);
        sql.append("VALUES(:product_name , :category, :image_url, :price, :stock, :description, :created_date, :last_modified_date)");

        Map<String, Object> map = new HashMap<>();

        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());


        Date date = new Date();
        map.put("created_date", date);
        map.put("last_modified_date", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql.toString(), new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();


    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE mall.product").append(new_line);
        sql.append("SET product_name = :product_name , category = :category, image_url = :image_url,  price = :price, stock = :stock, description = :description ,last_modified_date = :last_modified_date").append(new_line);
        sql.append("WHERE product_id = :product_id");

        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());


        Date date = new Date();
        map.put("last_modified_date", date);

        jdbcTemplate.update(sql.toString(), map);
    }
}
