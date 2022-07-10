package com.learn.translation.violet.service;

import com.learn.translation.violet.common.ApiResponse;
import com.learn.translation.violet.model.entity.User;
import com.learn.translation.violet.model.param.UserParam;
import com.learn.translation.violet.myenum.Role;
import com.learn.translation.violet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public ApiResponse createUser(UserParam userParam, Role role) {
        User user = userParam.convertToUser(role);

        String pass = user.getPassword();
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(pass);
        user.setPassword(hashPass);

        userRepository.save(user);
        return new ApiResponse(true, "User created");
    }

    public Optional<User> getUser(String username){
        return userRepository.findByUsername(username);
    }
}
