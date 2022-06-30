package com.learn.translation.deepLearnEnglish.model.dto;

import com.learn.translation.deepLearnEnglish.model.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Date createTime;
    private Date updateTime;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createTime = user.getCreateTime();
        this.updateTime = user.getUpdateTime();
    }
}
