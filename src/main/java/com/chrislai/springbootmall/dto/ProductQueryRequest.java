package com.chrislai.springbootmall.dto;

import com.chrislai.springbootmall.constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryRequest {
    private ProductCategory category;
    private String search;
}
