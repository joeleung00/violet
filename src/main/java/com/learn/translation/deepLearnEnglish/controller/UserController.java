package com.learn.translation.deepLearnEnglish.controller;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.dto.UserDto;
import com.learn.translation.deepLearnEnglish.model.entity.User;
import com.learn.translation.deepLearnEnglish.model.param.UserParam;
import com.learn.translation.deepLearnEnglish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
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
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid UserParam userParam) {
        ApiResponse response = userService.createUser(userParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
