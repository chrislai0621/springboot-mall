package com.chrislai.springbootmall.dao.impl;

import com.chrislai.springbootmall.constant.ProductCategory;
import com.chrislai.springbootmall.dao.ProductDao;
import com.chrislai.springbootmall.dao.rowmapper.ProductRowMapper;
import com.chrislai.springbootmall.dto.ProductQueryRequest;
import com.chrislai.springbootmall.dto.ProductRequest;
import com.chrislai.springbootmall.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryRequest productQueryRequest) {
        String sql = "Select product_id,product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date from product" +
                " where 1=1";
        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql,map,productQueryRequest);
        //排序
        sql += " ORDER BY " + productQueryRequest.getOrderBy() + " " + productQueryRequest.getSort();

        //分頁
        //取得1-24筆 limit=24&offset=0
        //取得24-48筆 limit=24&offset=24
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryRequest.getLimit());
        map.put("offset", productQueryRequest.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }
    @Override
    public Integer countProduct(ProductQueryRequest productQueryRequest) {
        String sql = "Select  count(*) from product" +
                " where 1=1";
        Map<String, Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,productQueryRequest);
        //queryForObject 通常用在COUNT
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

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

    @Override
    public Integer createProduct(ProductRequest request) {
        String sql = "INSERT INTO product (product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date ) VALUES (:productName, :category, :imageUrl, :price," +
                ":stock, :description, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", request.getProductName());
        map.put("category", request.getCategory().toString());
        map.put("imageUrl", request.getImageUrl());
        map.put("price", request.getPrice());
        map.put("stock", request.getStock());
        map.put("description", request.getDescription());
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest request) {
        String sql = "UPDATE product SET product_name = :productName, " +
                " category = :category, " +
                " image_url = :imageUrl, " +
                " price = :price, " +
                " stock = :stock, " +
                " description = :description, " +
                " last_Modified_date =:lastModifiedDate " +
                " where product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", request.getProductName());
        map.put("category", request.getCategory().toString());
        map.put("imageUrl", request.getImageUrl());
        map.put("price", request.getPrice());
        map.put("stock", request.getStock());
        map.put("description", request.getDescription());
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product   where product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    /**
     * 查詢條件
     * @param sql
     * @param map
     * @param productQueryRequest
     * @return
     */
    private String addFilteringSql(String sql,Map<String,Object> map,ProductQueryRequest productQueryRequest)
    {
        if (productQueryRequest.getCategory() != null) {
            sql += " AND category = :category";
            map.put("category", productQueryRequest.getCategory().name());
        }
        if (productQueryRequest.getSearch() != null) {
            sql += " AND product_name like :search";
            map.put("search", "%" + productQueryRequest.getSearch() + "%");
        }
        return  sql;
    }
    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock, " +
                " last_Modified_date =:lastModifiedDate " +
                " where product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("stock", stock);
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

}
