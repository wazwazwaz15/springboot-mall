package com.bowei.springbootmall.service.imple;

import com.bowei.springbootmall.dao.UserDao;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.bowei.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImple implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
