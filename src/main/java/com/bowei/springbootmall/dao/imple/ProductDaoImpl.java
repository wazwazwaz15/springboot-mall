package com.bowei.springbootmall.dao.imple;


import com.bowei.springbootmall.dao.ProductDao;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.model.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
}
