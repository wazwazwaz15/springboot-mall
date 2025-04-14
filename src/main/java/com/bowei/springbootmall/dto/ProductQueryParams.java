package com.bowei.springbootmall.dto;

import com.bowei.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParams {
private ProductCategory category;
private String search;


}
