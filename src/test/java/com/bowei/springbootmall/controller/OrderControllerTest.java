package com.bowei.springbootmall.controller;

import com.bowei.springbootmall.dto.BuyItem;
import com.bowei.springbootmall.dto.CreateOrderRequest;
import com.bowei.springbootmall.dto.UserLoginRequest;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;


    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
    public void createOrder_success() throws Exception {

        UserRegisterRequest UserRegisterRequest = new UserRegisterRequest();
        UserRegisterRequest.setEmail("wazwazwaz15@gmail.com");
        UserRegisterRequest.setPassword("testPassword");

        RequestBuilder registerRequest = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(UserRegisterRequest));


       MvcResult result = mockMvc.perform(registerRequest)
                .andExpect(status().isCreated())
                .andReturn();

       User u = mapper.readValue(result.getResponse().getContentAsString(), User.class);


        System.out.println("使用者資訊:"+result.getResponse().getContentAsString());



        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("wazwazwaz15@gmail.com");
        userLoginRequest.setPassword("testPassword");

        RequestBuilder loginRequest = MockMvcRequestBuilders
                .post("/users/login2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userLoginRequest));

        MvcResult loginResult= mockMvc.perform(loginRequest)
                .andExpect(status().isOk())
                .andReturn();



        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);


        assertNotNull(session);




        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(10);

        BuyItem buyItem2 = new BuyItem();
        buyItem2.setProductId(2);
       buyItem2.setQuantity(4);

        buyItemList.add(buyItem);
        buyItemList.add(buyItem2);

        createOrderRequest.setBuyItemList(buyItemList);
        String json = mapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", u.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .session(session);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId",equalTo(u.getUserId())))
                .andExpect(jsonPath("$.orderId",equalTo(3)))
                .andExpect(jsonPath("$.totalAmount",notNullValue()))
                .andExpect(jsonPath("$.createdDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()));
    }

    @Test
    @Transactional
    public void createOrder_userNotExists() throws Exception {

        UserRegisterRequest UserRegisterRequest = new UserRegisterRequest();
        UserRegisterRequest.setEmail("test@gmail.com");
        UserRegisterRequest.setPassword("testPassword");

        RequestBuilder registerRequest = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(UserRegisterRequest));


        mockMvc.perform(registerRequest)
                .andExpect(status().isCreated());


        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@gmail.com");
        userLoginRequest.setPassword("testPassword");

        RequestBuilder loginRequest = MockMvcRequestBuilders
                .post("/users/login2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userLoginRequest));

        MvcResult loginResult= mockMvc.perform(loginRequest)
                .andExpect(status().isOk())
                .andReturn();



        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);


        assertNotNull(session);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(10);

        BuyItem buyItem2 = new BuyItem();
        buyItem2.setProductId(2);
        buyItem2.setQuantity(4);

        buyItemList.add(buyItem);
        buyItemList.add(buyItem2);

        createOrderRequest.setBuyItemList(buyItemList);
        String json = mapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).session(session);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }




    @Test
    @Transactional
    public void createOrder_outOfStock() throws Exception {

        UserRegisterRequest UserRegisterRequest = new UserRegisterRequest();
        UserRegisterRequest.setEmail("test3@gmail.com");
        UserRegisterRequest.setPassword("testPassword");

        RequestBuilder registerRequest = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(UserRegisterRequest));


        MvcResult result = mockMvc.perform(registerRequest)
                .andExpect(status().isCreated())
                .andReturn();

        User u = mapper.readValue(result.getResponse().getContentAsString(), User.class);


        System.out.println("使用者資訊:"+result.getResponse().getContentAsString());



        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test3@gmail.com");
        userLoginRequest.setPassword("testPassword");

        RequestBuilder loginRequest = MockMvcRequestBuilders
                .post("/users/login2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userLoginRequest));

        MvcResult loginResult= mockMvc.perform(loginRequest)
                .andExpect(status().isOk())
                .andReturn();



        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);


        assertNotNull(session);


        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(100);

        BuyItem buyItem2 = new BuyItem();
        buyItem2.setProductId(2);
        buyItem2.setQuantity(400);

        buyItemList.add(buyItem);
        buyItemList.add(buyItem2);

        createOrderRequest.setBuyItemList(buyItemList);
        String json = mapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", u.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).session(session);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }





    @Test
    @Transactional
    public void getOrders() throws Exception {

        UserRegisterRequest UserRegisterRequest = new UserRegisterRequest();
        UserRegisterRequest.setEmail("wazwazwaz15@gmail.com");
        UserRegisterRequest.setPassword("testPassword");

        RequestBuilder registerRequest = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(UserRegisterRequest));


        mockMvc.perform(registerRequest)
                .andExpect(status().isCreated())
                .andReturn();


        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("wazwazwaz15@gmail.com");
        userLoginRequest.setPassword("testPassword");

        RequestBuilder loginRequest = MockMvcRequestBuilders
                .post("/users/login2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userLoginRequest));

        MvcResult loginResult= mockMvc.perform(loginRequest)
                .andExpect(status().isOk())
                .andReturn();



        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);


        assertNotNull(session);


        RequestBuilder request = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 1)
                .param("limit", "20")
                .param("offset", "1")
                .session(session);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.limit", equalTo(20)))
                .andExpect(jsonPath("$.offset", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(2)))
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results", notNullValue()))
                .andReturn();

    }









}