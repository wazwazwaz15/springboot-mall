package com.bowei.springbootmall.controller;

import com.bowei.springbootmall.dto.CreateOrderRequest;
import com.bowei.springbootmall.dto.OrderQueryParams;
import com.bowei.springbootmall.model.Order;
import com.bowei.springbootmall.service.OrderService;
import com.bowei.springbootmall.util.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);



        return ResponseEntity.status(HttpStatus.CREATED).body(order);


    }


    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable(required = false) Integer userId,
                                                 @RequestParam(defaultValue = "10") @Max(1000) @Min(10) Integer limit,
                                                 @RequestParam(defaultValue = "0") @Min(0) Integer offset) {





        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);


        //取得OrderList

        List<Order> orderList = orderService.getOrders(orderQueryParams);

        //取得 order 整數
        Integer count = orderService.countOrder(orderQueryParams);


        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }


}
