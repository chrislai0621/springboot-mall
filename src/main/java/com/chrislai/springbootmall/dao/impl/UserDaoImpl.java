package com.chrislai.springbootmall.dao.impl;

import com.chrislai.springbootmall.dao.UserDao;
import com.chrislai.springbootmall.dao.rowmapper.UserRowMapper;
import com.chrislai.springbootmall.dto.UserRegisterRequest;
import com.chrislai.springbootmall.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user(email,password, created_date,last_modified_date) VALUES(:email,:password,:createdDate,:lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        Date now = new Date();
        map.put("password", userRegisterRequest.getPassword());
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int userId = keyHolder.getKey().intValue();
        return userId;
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "Select user_id,email,password, created_date,last_modified_date from user where user_id =:userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

       List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if (!userList.isEmpty()) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "Select user_id,email,password, created_date,last_modified_date from user where email =:email";
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if (!userList.isEmpty()) {
            return userList.get(0);
        } else {
            return null;
        }
    }


}
