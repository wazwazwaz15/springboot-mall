package com.bowei.springbootmall.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuyItem {
    @NotNull
    private Integer productId;
    @NotNull
    public Integer quantity;
}
