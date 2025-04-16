package com.bowei.springbootmall.dao;

import com.bowei.springbootmall.dto.OrderQueryParams;
import com.bowei.springbootmall.model.Order;
import com.bowei.springbootmall.model.OrderItem;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, int totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
