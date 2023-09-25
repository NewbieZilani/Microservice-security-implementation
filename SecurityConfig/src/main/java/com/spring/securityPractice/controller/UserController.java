package com.spring.securityPractice.controller;

import com.spring.securityPractice.constants.AppConstants;
import com.spring.securityPractice.model.UserDto;
import com.spring.securityPractice.service.UserService;
import com.spring.securityPractice.service.impl.UserServiceImpl;
import com.spring.securityPractice.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello access by user";
    }

    @GetMapping("/hello1")
    public String hello1(){
        return "Hello 1 access by admin";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "Hello 2 access by admin";
    }

    @GetMapping("/hello3")
    public String hello3(){
        return "Hello 3 can access by users also";
    }


    @PostMapping("/registration")
    public ResponseEntity<?> register (@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            String accessToken = JWTUtils.generateToken(createdUser.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("user", createdUser);
            response.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + accessToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
