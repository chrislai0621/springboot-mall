package com.chrislai.springbootmall.dao.impl;

import com.chrislai.springbootmall.dao.ProductDao;
import com.chrislai.springbootmall.dao.rowmapper.ProductRowMapper;
import com.chrislai.springbootmall.model.Product;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductId(Integer productId) {
        String sql = "Select product_id,product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date from product where product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if (!productList.isEmpty()) {
            return productList.get(0);
        } else {
            return null;
        }

    }
}
