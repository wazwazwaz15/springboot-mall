package com.bowei.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class User {


    private Integer userId;
    private String email;

    @JsonIgnore
    private String password;


    private String role;

    private Timestamp createdDate;
    private Timestamp lastModifiedDate;


}
