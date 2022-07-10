package com.learn.translation.violet.model.param;

import com.learn.translation.violet.model.entity.User;
import com.learn.translation.violet.myenum.Role;
import lombok.Data;

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

    public User convertToUser(Role role){
        User user = new User();
        user.setEmail(this.email);
        user.setFirstname(this.firstname);
        user.setLastname(this.lastname);
        user.setPassword(this.password);
        user.setUsername(this.username);
        user.setRole(role.toString());
        return user;
    }
}
