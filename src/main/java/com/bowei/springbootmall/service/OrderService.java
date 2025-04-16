package com.bowei.springbootmall.service;

import com.bowei.springbootmall.dto.CreateOrderRequest;
import com.bowei.springbootmall.dto.OrderQueryParams;
import com.bowei.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
