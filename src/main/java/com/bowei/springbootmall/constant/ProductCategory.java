package com.bowei.springbootmall.constant;

import org.springframework.data.relational.core.sql.In;

public enum ProductCategory {

    FOOD(1),
    CAR(2),
    BOOK(3);

    private final Integer displayId;

    ProductCategory(Integer displayId) {
        this.displayId = displayId;
    }

    public Integer getDisplayId() {
        return displayId;
    }

}
