package com.bowei.springbootmall.service.imple;

import com.bowei.springbootmall.dao.UserDao;
import com.bowei.springbootmall.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.getUserByEmail(email);
        if (user == null) {
            log.warn("使用者不存在 {}", email);
            throw new UsernameNotFoundException("使用者不存在 " + email);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // 必須是加密過的密碼
                .roles(user.getRole()) // 你可以之後從 DB 拿角色
                .build();
    }

}
