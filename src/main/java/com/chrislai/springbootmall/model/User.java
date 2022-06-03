package com.chrislai.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private Integer userId;
    //客製化回傳給前端的參數值
    //@JsonProperty("e_mail")
    private String email;
    //轉換JSON OBJ時，就會忽略這個參數
    @JsonIgnore
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;
}
