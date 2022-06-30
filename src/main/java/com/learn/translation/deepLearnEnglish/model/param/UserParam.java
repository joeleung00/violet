package com.learn.translation.deepLearnEnglish.model.param;

import com.learn.translation.deepLearnEnglish.model.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserParam {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @Email
    @NotBlank
    @Size(max = 127)
    private String email;

    public User convertToUser(){
        User user = new User();
        user.setEmail(this.email);
        user.setFirstname(this.firstname);
        user.setLastname(this.lastname);
        user.setPassword(this.password);
        user.setUsername(this.username);
        return user;
    }
}
