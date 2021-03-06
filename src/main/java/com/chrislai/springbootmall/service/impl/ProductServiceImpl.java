package com.chrislai.springbootmall.service.impl;

import com.chrislai.springbootmall.constant.ProductCategory;
import com.chrislai.springbootmall.dao.ProductDao;
import com.chrislai.springbootmall.dao.impl.ProductDaoImpl;
import com.chrislai.springbootmall.dto.ProductQueryRequest;
import com.chrislai.springbootmall.dto.ProductRequest;
import com.chrislai.springbootmall.model.Product;
import com.chrislai.springbootmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryRequest productQueryRequest){
        return productDao.getProducts(productQueryRequest);
    }

    @Override
    public Integer countProduct(ProductQueryRequest productQueryRequest) {
        return productDao.countProduct(productQueryRequest);
    }

    @Override
    public Product getProductId(Integer productId) {
        return productDao.getProductId(productId);
    }

    @Override
    public Integer createProduct(ProductRequest request) {
        return productDao.createProduct(request);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest request) {
        productDao.updateProduct(productId, request);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }


}
