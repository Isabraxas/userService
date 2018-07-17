package com.example.userService.domain;

import com.example.userService.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name="BD_INTERNAL_USER")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BD_USR_ID")
    private Long userId;

    @Column(name = "BD_USR_CODE")
    //@NotNull
    private String userCode;

    @Column(name = "BD_USR_FULL_NAME")
    //@NotNull
    private String userName;

    @Column(name = "BD_USR_PASSWORD")
    //@NotNull
    private String userPassword;

    @Column(name = "BD_USR_CREATE_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp userCreateTime;

    public UserModel() {
    }

    public UserModel(Long userId, String userCode, String userName, String userPassword, Timestamp userCreateTime) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userCreateTime = userCreateTime;
    }

    public UserModel(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.userCode = userDto.getUserCode();
        this.userName = userDto.getUserName();
        this.userPassword = userDto.getUserPassword();
        this.userCreateTime = userDto.getUserCreateTime();
    }


}
