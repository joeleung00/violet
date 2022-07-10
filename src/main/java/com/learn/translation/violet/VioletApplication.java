package com.learn.translation.violet;

import com.learn.translation.violet.model.entity.User;
import com.learn.translation.violet.model.param.UserParam;
import com.learn.translation.violet.myenum.Role;
import com.learn.translation.violet.repository.UserRepository;
import com.learn.translation.violet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SpringBootApplication
public class VioletApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	@Override
	public void run(String...args) throws Exception {
		// create admin user, set environment variable in your intellij RUN/DEBUG config
		UserParam admin = new UserParam();
		admin.setFirstname("admin");
		admin.setLastname("admin");
		admin.setEmail("admin@test.com");
		admin.setUsername(System.getenv("VIOLET_USER"));
		admin.setPassword(System.getenv("VIOLET_PASS"));
		Optional<User> optionalUser = userService.getUser(admin.getUsername());
		if (optionalUser.isEmpty()){
			userService.createUser(admin, Role.ADMIN);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(VioletApplication.class, args);
	}
}



