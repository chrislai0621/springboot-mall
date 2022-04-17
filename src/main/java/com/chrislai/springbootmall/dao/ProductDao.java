package com.chrislai.springbootmall.dao;

import com.chrislai.springbootmall.dto.ProductRequest;
import com.chrislai.springbootmall.model.Product;

public interface ProductDao {
    Product getProductId(Integer productId);

    Integer createProduct(ProductRequest request);

    void updateProduct(Integer productId, ProductRequest request);

}
