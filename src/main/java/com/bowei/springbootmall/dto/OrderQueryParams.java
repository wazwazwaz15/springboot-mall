package com.bowei.springbootmall.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.relational.core.sql.In;

@Data
public class OrderQueryParams {

    private Integer userId;


    private Integer limit;


    private Integer offset;


}
