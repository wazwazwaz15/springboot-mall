package com.bowei.springbootmall.service;

import com.bowei.springbootmall.dto.CreateOrderRequest;
import com.bowei.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
