package com.learn.translation.violet.controller;

import com.learn.translation.violet.common.ApiResponse;
import com.learn.translation.violet.model.dto.UserDto;
import com.learn.translation.violet.model.entity.User;
import com.learn.translation.violet.model.param.UserParam;
import com.learn.translation.violet.myenum.Role;
import com.learn.translation.violet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault(sort = {"id"}, size = 20)Pageable pageable) {
        Page<User> userPage = userService.getAllUsers(pageable);
        Page<UserDto> userDtoPage = new PageImpl<>(
                userPage.getContent().stream().map(u -> new UserDto(u)).collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );
        return new ResponseEntity(userDtoPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> registerAdminUser(@RequestBody @Valid UserParam userParam) {
        ApiResponse response = userService.createUser(userParam, Role.ADMIN);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
