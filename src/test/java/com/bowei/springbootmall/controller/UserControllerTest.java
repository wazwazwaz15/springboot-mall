package com.bowei.springbootmall.controller;

import com.bowei.springbootmall.dao.UserDao;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDao userDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void register_success() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("wazwazwaz16@gmail.com");
        userRegisterRequest.setPassword("aA243703433");

      String json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder request = MockMvcRequestBuilders.post("/users/register")
                .contentType("application/json")
                .content(json);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId",notNullValue()))
                .andExpect(jsonPath("$.email",equalTo("wazwazwaz16@gmail.com")));


        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        System.out.println(user);
        assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());



    }

    @Test
    @Transactional
    public void register_wrongEmailFormat() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("3sdfsdf");
        userRegisterRequest.setPassword("aA243703433");

        String json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder request = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));

    }
    @Test
    @Transactional
    public void register_emailExist() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("wazwazwaz15@gmail.com");
        userRegisterRequest.setPassword("aA243703433");
        String json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder request = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(201));


        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));


    }


    @Test
    public void  Login_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("wazwazwaz15@gmail.com");

        String json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder request = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));





    }




}