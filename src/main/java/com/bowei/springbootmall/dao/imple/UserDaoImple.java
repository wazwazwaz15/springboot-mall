package com.bowei.springbootmall.dao.imple;

import com.bowei.springbootmall.dao.UserDao;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.bowei.springbootmall.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImple implements UserDao {

    private final String NEW_LINE = System.getProperty("line.separator");

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplatel;


    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO user(email,password,created_date,last_modified_date)").append(NEW_LINE);
        sql.append("VALUES(:email , :password , :createdDate , :lastModifiedDate)");

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        Date date = new Date();

        map.put("createdDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplatel.update(sql.toString(), new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();

    }

    @Override
    public User getUserById(Integer userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT user_id  , email, password,created_date,last_modified_date ").append(NEW_LINE);
        sql.append("FROM user").append(NEW_LINE);
        sql.append("WHERE user_id = :userId");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);


        List<User> userList = namedParameterJdbcTemplatel.query(sql.toString(), map, new UserRowMapper());

        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }

    }
}


