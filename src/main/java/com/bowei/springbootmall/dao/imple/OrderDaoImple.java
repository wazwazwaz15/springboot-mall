package com.bowei.springbootmall.dao.imple;

import com.bowei.springbootmall.dao.OrderDao;
import com.bowei.springbootmall.model.Order;
import com.bowei.springbootmall.model.OrderItem;
import com.bowei.springbootmall.rowmapper.OrderItemRowMapper;
import com.bowei.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImple implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String NEW_LINE = System.lineSeparator();


    @Override
    public Integer createOrder(Integer userId, int totalAmount) {

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO `order` (user_id , total_amount , created_date , last_modified_date )").append(NEW_LINE);
        sql.append("VALUES (:userId , :totalAmount , :createdDate , :lastModifiedDate )").append(NEW_LINE);

        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date date = new Date();

        map.put("createdDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();

    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO order_item (order_id , product_id , quantity , amount )").append(NEW_LINE);
        sql.append("VALUES (:order_id , :product_id , :quantity , :amount )").append(NEW_LINE);

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("order_id", orderId);
            parameterSources[i].addValue("product_id", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql.toString(), parameterSources);


    }

    @Override
    public Order getOrderById(Integer orderId) {


        StringBuilder sql = new StringBuilder();

        sql.append("SELECT order_id , user_id , total_amount , created_date , last_modified_date  FROM  `order` ").append(NEW_LINE);
        sql.append("WHERE order_id = :orderId").append(NEW_LINE);

        Map<String, Object> map = new HashMap<>();

        map.put("orderId", orderId);


      List<Order> orderList =  namedParameterJdbcTemplate.query(sql.toString(), new MapSqlParameterSource(map), new OrderRowMapper());

   if( orderList.size() > 0) {
       return orderList.get(0);
   }else {
       return null;
   }



    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {


        StringBuilder sql = new StringBuilder();

        sql.append("SELECT order_item_id , order_id  , p.product_id as product_id, quantity , amount , product_name , image_url ").append(NEW_LINE);
        sql.append("FROM  order_item as oi ").append(NEW_LINE);
        sql.append("LEFT JOIN product  as p ").append(NEW_LINE);
        sql.append("ON oi.product_id = p.product_id ").append(NEW_LINE);
        sql.append("WHERE order_id = :orderId").append(NEW_LINE);

        Map<String, Object> map = new HashMap<>();

        map.put("orderId", orderId);


        List<OrderItem> orderItemList =  namedParameterJdbcTemplate.query(sql.toString(), map, new OrderItemRowMapper());


        return orderItemList;
    }
}
