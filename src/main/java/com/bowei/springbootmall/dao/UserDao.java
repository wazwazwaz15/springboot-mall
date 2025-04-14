package com.bowei.springbootmall.dao;

import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public interface UserDao {


    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
