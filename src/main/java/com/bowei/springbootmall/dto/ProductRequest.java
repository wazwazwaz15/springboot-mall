package com.bowei.springbootmall.dto;

import com.bowei.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ProductRequest {
    @NotBlank
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotBlank
    private String imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;

    private String description;

}
