package com.example.userService.dto;

import com.example.userService.domain.UserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class UserDto implements Serializable {

    private Long userId;

    private String userCode;

    private String userName;

    private String userPassword;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp userCreateTime;

    public UserDto() {
    }

    public UserDto(Long userId, String userCode, String userName, String userPassword, Timestamp userCreateTime) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userCreateTime = userCreateTime;
    }

    public UserDto(UserModel userModel) {
        this.userId = userModel.getUserId();
        this.userCode = userModel.getUserCode();
        this.userName = userModel.getUserName();
        this.userPassword = userModel.getUserPassword();
        this.userCreateTime = userModel.getUserCreateTime();
    }


}
