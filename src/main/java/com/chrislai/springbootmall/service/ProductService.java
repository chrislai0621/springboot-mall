package com.chrislai.springbootmall.service;

import com.chrislai.springbootmall.dto.ProductRequest;
import com.chrislai.springbootmall.model.Product;

public interface ProductService {
    Product getProductId(Integer productId);

    Integer createProduct(ProductRequest request);

    void updateProduct(Integer productId, ProductRequest request);
}
