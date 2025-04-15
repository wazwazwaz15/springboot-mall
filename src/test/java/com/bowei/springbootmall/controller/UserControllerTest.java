package com.bowei.springbootmall.controller;

import com.bowei.springbootmall.dao.UserDao;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.bowei.springbootmall.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
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

    @Autowired
    private UserDetailsService userService;


    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
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
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo("wazwazwaz16@gmail.com")));


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
    @Transactional
    public void Login_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("wazwazwaz15@gmail.com");
        userRegisterRequest.setPassword("aA243703433");
        String json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder request = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(201));

        RequestBuilder loginRequest = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(loginRequest)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email", equalTo("wazwazwaz15@gmail.com")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));

    }


    @Test
    @Transactional
    public void Login_invalidArguments() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("wazwazwaz15@gmail.com");
        userRegisterRequest.setPassword("aA243703433");
        String json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder request = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(201));

        userRegisterRequest.setEmail("wazwazwaz16@gmail.com");
        userRegisterRequest.setPassword("aA243703433");
        json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder loginRequest = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(loginRequest)
                .andExpect(status().is(400));

    }

    @Test
    @Transactional
    public void Login2_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("wazwazwaz15@gmail.com");
        userRegisterRequest.setPassword("aA243703433");
        String json = mapper.writeValueAsString(userRegisterRequest);

        RequestBuilder request = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(201));


        RequestBuilder loginRequest = MockMvcRequestBuilders.post("/users/login2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);


        userService.loadUserByUsername("wazwazwaz15@gmail.com");


        MvcResult loginResult = mockMvc.perform(loginRequest)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);

        RequestBuilder meRequest = MockMvcRequestBuilders.get("/users/me").session(session);
        mockMvc.perform(meRequest)
                .andExpect(status().is(200));


    }
}