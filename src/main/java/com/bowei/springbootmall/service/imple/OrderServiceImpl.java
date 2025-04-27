package com.bowei.springbootmall.service.imple;

import com.bowei.springbootmall.dao.OrderDao;
import com.bowei.springbootmall.dao.ProductDao;
import com.bowei.springbootmall.dao.UserDao;
import com.bowei.springbootmall.dto.BuyItem;
import com.bowei.springbootmall.dto.CreateOrderRequest;
import com.bowei.springbootmall.dto.OrderQueryParams;
import com.bowei.springbootmall.model.Order;
import com.bowei.springbootmall.model.OrderItem;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.model.User;
import com.bowei.springbootmall.redis.RedisUtil;
import com.bowei.springbootmall.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;


    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        User user = null;

        //Key 格式  key::id
        user = (User) redisUtil.getObject("userId::" + userId);

        //當redis不存在這筆資料存進Redis
        if (user == null) {
            user = userDao.getUserById(userId);
            redisUtil.setObject("userId::" + userId, user);
        }


        //檢查使用者是否存在
        if (user == null) {
            logger.warn("該使用者 {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        int totalAmount = 0;

        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());


            //檢查 product 是否存在、庫存是否足夠
            if (product == null) {
                logger.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                logger.warn("商品 {} 庫存數量不足 ， 無法購買 。 剩餘庫存 {} ，欲購買數量 {}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }


            //扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());


            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            //轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    @Cacheable(value = "orderIdCache", key = "#root.args[0]", unless = "#result == null")
    public Order getOrderById(Integer orderId) {
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        Order order = orderDao.getOrderById(orderId);
        order.setOrderItems(orderItemList);
        return order;
    }

    @CacheEvict(value = "orderIdCache",allEntries = true)
    public void cleanOrderIdCache(){

    }




    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);
        for (Order order : orderList) {
            order.setOrderItems(orderDao.getOrderItemsByOrderId(order.getOrderId()));
        }


        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {


        return orderDao.countOrder(orderQueryParams);
    }
}
