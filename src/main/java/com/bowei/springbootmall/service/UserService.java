package com.bowei.springbootmall.service;

import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
