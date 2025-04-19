package com.bowei.springbootmall.controller;


import com.bowei.springbootmall.dto.UserLoginRequest;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.bowei.springbootmall.service.UserService;
import com.bowei.springbootmall.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {


        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        User user = userService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //搭配SpringSecurity的Security Filter Chain

    @PostMapping("/users/login2")
    public ResponseEntity<?> login2(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        //設定 AuthenticationManager
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(), userLoginRequest.getPassword()
                ));
        String token = jwtUtil.generateToken(userLoginRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));

    }


    @GetMapping("/users/me")
    public ResponseEntity<String> me(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.OK).body("目前登入者:" + user.getUsername());

    }

}
