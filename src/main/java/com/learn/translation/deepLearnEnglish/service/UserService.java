package com.learn.translation.deepLearnEnglish.service;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.dto.UserDto;
import com.learn.translation.deepLearnEnglish.model.entity.User;
import com.learn.translation.deepLearnEnglish.model.param.UserParam;
import com.learn.translation.deepLearnEnglish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public ApiResponse createUser(UserParam userParam) {
        User user = userParam.convertToUser();
        userRepository.save(user);
        return new ApiResponse(true, "User created");
    }
}
