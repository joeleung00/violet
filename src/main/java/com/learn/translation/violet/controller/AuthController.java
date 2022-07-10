package com.learn.translation.violet.controller;

import com.learn.translation.violet.common.ApiResponse;
import com.learn.translation.violet.model.dto.UserDto;
import com.learn.translation.violet.model.entity.User;
import com.learn.translation.violet.model.param.UserParam;
import com.learn.translation.violet.myenum.Role;
import com.learn.translation.violet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserParam userParam) {
        return new ResponseEntity<>(userService.createUser(userParam, Role.USER), HttpStatus.OK);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUser(userDetails.getUsername()).get();
        UserDto userDto = new UserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
