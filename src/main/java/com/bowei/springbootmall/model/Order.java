package com.bowei.springbootmall.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
public class Order {

    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private List<OrderItem> orderItems;

}
