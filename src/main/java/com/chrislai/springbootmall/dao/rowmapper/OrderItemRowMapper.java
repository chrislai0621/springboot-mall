package com.chrislai.springbootmall.dao.rowmapper;

import com.chrislai.springbootmall.model.Order;
import com.chrislai.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem item = new OrderItem();
        item.setOrderItemId(rs.getInt("order_item_id"));
        item.setOrderId(rs.getInt("order_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setAmount(rs.getInt("amount"));

        item.setProductName(rs.getString("product_name"));
        item.setImageUrl(rs.getString("image_url"));
        return item;
    }
}
