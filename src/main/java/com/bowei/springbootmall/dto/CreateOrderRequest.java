package com.bowei.springbootmall.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @NotEmpty
    private List<BuyItem> buyItemList;
}
