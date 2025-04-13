package com.bowei.springbootmall.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product {


    private Integer productId;
    private String productName;
    private String category;
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;


}
