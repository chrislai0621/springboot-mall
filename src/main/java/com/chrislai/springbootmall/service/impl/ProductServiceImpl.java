package com.chrislai.springbootmall.service.impl;

import com.chrislai.springbootmall.dao.ProductDao;
import com.chrislai.springbootmall.dao.impl.ProductDaoImpl;
import com.chrislai.springbootmall.model.Product;
import com.chrislai.springbootmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    @Override
    public Product getProductId(Integer productId) {
        return productDao.getProductId(productId);
    }
}
