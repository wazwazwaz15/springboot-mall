package com.bowei.springbootmall.service.imple;
import com.bowei.springbootmall.dao.OrderDao;
import com.bowei.springbootmall.dao.ProductDao;
import com.bowei.springbootmall.dto.BuyItem;
import com.bowei.springbootmall.dto.CreateOrderRequest;
import com.bowei.springbootmall.model.Order;
import com.bowei.springbootmall.model.OrderItem;
import com.bowei.springbootmall.model.Product;
import com.bowei.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;


    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;

        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());


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
@Transactional
    public Order getOrderById(Integer orderId) {
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        Order order = orderDao.getOrderById(orderId);
        order.setOrderItems(orderItemList);
        return order;

    }
}
