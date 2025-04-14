package com.bowei.springbootmall.constant;

import lombok.Getter;

@Getter
public enum ProductCategory {

    FOOD(1),
    CAR(2),
    BOOK(3);

    private final Integer displayId;

    ProductCategory(Integer displayId) {
        this.displayId = displayId;
    }

}
