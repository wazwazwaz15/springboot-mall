package com.bowei.springbootmall.controller;


import com.bowei.springbootmall.dto.UserLoginRequest;
import com.bowei.springbootmall.dto.UserRegisterRequest;
import com.bowei.springbootmall.model.User;
import com.bowei.springbootmall.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;


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
    public ResponseEntity<String> login2(@RequestBody @Valid UserLoginRequest userLoginRequest, HttpServletRequest request) {
        //設定 AuthenticationManager
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginRequest.getEmail(), userLoginRequest.getPassword()
        ));

        System.out.println("登入使用者: " + auth.getName());
        System.out.println("使用者角色: " + auth.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return ResponseEntity.status(HttpStatus.OK).body("登入成功! 使用者: " + auth.getName());

    }


    @GetMapping("/users/me")
    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "未登入或無法識別";
        }
        System.out.println("目前登入使用者: " + auth.getName());
        System.out.println("使用者角色: " + auth.getAuthorities());
        return "Hello, " + auth.getName();
    }

}
