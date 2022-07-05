package com.chrislai.springbootmall.dto;

import com.chrislai.springbootmall.constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderQueryParams {
    private Integer userId;
    private Integer limit;
    private Integer offset;
}
