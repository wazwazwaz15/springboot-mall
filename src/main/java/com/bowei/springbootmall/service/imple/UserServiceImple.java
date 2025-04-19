package com.bowei.springbootmall.service.imple;

import com.bowei.springbootmall.dao.UserDao;
import com.bowei.springbootmall.dto.UserLoginRequest;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.bowei.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImple implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImple.class);

    //使用 BCryptPasswordEncoder 生成密碼的雜湊值
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public Integer register(UserRegisterRequest userRegisterRequest) {


        // 檢查註冊的 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }

        userRegisterRequest.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));

        //創建帳號
        return userDao.createUser(userRegisterRequest);

    }


    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());


        //檢查 user 是否存在
        if (user == null) {
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //比較密碼
        if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            return user;
        } else {
            log.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);


        }
    }



    //使用 MD5 生成密碼的雜湊值 (雜湊過快，容易被破解、已被淘汰)
//    private String encodedPassWord(String password) {
//        return DigestUtils.md5DigestAsHex(password.getBytes());
//    }


}
