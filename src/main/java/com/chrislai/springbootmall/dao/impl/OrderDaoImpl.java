package com.chrislai.springbootmall.dao.impl;

import com.chrislai.springbootmall.dao.OrderDao;
import com.chrislai.springbootmall.dao.rowmapper.OrderItemRowMapper;
import com.chrislai.springbootmall.dao.rowmapper.OrderRowMapper;
import com.chrislai.springbootmall.model.Order;
import com.chrislai.springbootmall.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "Select order_id,user_id, total_amount, created_date, last_modified_date from `order` where order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if (!orderList.isEmpty()) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "Select oi.order_item_id,oi.order_id,oi.product_id,oi.quantity,oi.amount,p.product_name,p.image_url" +
                " from order_item as oi LEFT JOIN product as p ON oi.product_id = p.product_id" +
                " where oi.order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        if (!orderItemList.isEmpty()) {
            return orderItemList;
        } else {
            return null;
        }
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount,created_date, last_modified_date )" +
                "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    @Transactional
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        //使用LOOP 一筆筆插入
//        for (OrderItem item : orderItemList) {
//            String sql = "INSERT INTO order_item (order_id, product_id,quantity,amount )" +
//                    "VALUES (:orderId, :productId, :quantity, :amount)";
//            Map<String, Object> map = new HashMap<>();
//            map.put("orderId", orderId);
//            map.put("productId", item.getProductId());
//            map.put("quantity", item.getQuantity());
//            map.put("amount", item.getAmount());
//            namedParameterJdbcTemplate.update(sql, map);
//        }
        //使用BATCH UPDATE
        String sql = "INSERT INTO order_item (order_id, product_id,quantity,amount )" +
                "VALUES (:orderId, :productId, :quantity, :amount)";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);

    }
}
