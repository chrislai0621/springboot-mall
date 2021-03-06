package com.chrislai.springbootmall.service;

import com.chrislai.springbootmall.dto.ProductQueryRequest;
import com.chrislai.springbootmall.dto.ProductRequest;
import com.chrislai.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductQueryRequest productQueryRequest);

    Integer countProduct(ProductQueryRequest productQueryRequest);

    Product getProductId(Integer productId);

    Integer createProduct(ProductRequest request);

    void updateProduct(Integer productId, ProductRequest request);

    void deleteProduct(Integer productId);
}
