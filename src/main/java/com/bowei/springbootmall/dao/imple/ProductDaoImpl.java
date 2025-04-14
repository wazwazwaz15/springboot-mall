package com.bowei.springbootmall.dao.imple;


import com.bowei.springbootmall.dao.ProductDao;
import com.bowei.springbootmall.dto.ProductQueryParams;
import com.bowei.springbootmall.dto.ProductRequest;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.model.ProductRowMapper;
import com.bowei.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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

    @Override
    public void deleteProductId(Integer productId) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM mall.product WHERE product_id = :productId");

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        jdbcTemplate.update(sql.toString(), map);


    }

    @Override
    public    List<Product> getProducts(ProductQueryParams productQueryParams) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT product_id, product_name , category, image_url, price, stock, description, created_date, last_modified_date").append(new_line);
        sql.append("FROM mall.product").append(new_line);
        sql.append("WHERE 1 = 1").append(new_line);

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        if (productQueryParams.getCategory() != null) {
            sql.append("AND category = :category").append(new_line);
            map.put("category", productQueryParams.getCategory().name());
        }
        if (productQueryParams.getSearch() != null) {
            sql.append("AND product_name LIKE :product_name").append(new_line);
            map.put("product_name", "%" + productQueryParams.getSearch() + "%");
        }

        //排序條件
        sql.append(" ORDER BY ").append(productQueryParams.getOrderBy()).append(" ").append(productQueryParams.getSort()).append(new_line);

        //分頁條件
        sql.append("LIMIT :limit OFFSET :offset ").append(new_line);
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());


        return jdbcTemplate.query(sql.toString(), map, new ProductRowMapper());


    }

    @Override
    public Integer countProducts(ProductQueryParams params) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) ").append(new_line);
        sql.append("FROM mall.product").append(new_line);
        sql.append("WHERE 1 = 1").append(new_line);

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        if (params.getCategory() != null) {
            sql.append("AND category = :category").append(new_line);
            map.put("category", params.getCategory().name());
        }
        if (params.getSearch() != null) {
            sql.append("AND product_name LIKE :product_name").append(new_line);
            map.put("product_name", "%" + params.getSearch() + "%");
        }

        Integer total = jdbcTemplate.queryForObject(sql.toString(), map, Integer.class);

        return total;

    }


}
