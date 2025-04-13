package com.bowei.springbootmall.model;

import com.bowei.springbootmall.constant.ProductCategory;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product {


    private Integer productId;
    private String productName;
    private ProductCategory category;
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;


}
