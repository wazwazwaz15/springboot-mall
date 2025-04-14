package com.bowei.springbootmall.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;


@Data
public class User {


    private Integer userId;
    private String email ;
    private String password;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;



}
