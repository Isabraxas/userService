package com.example.userService.service;


import com.example.userService.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAll(Pageable pageable);

    UserDto getById(Long id);

    UserDto getByUserName(String username);

    UserDto getByUserCode(String code);

    UserDto save(UserDto user);

    UserDto update(UserDto user);

    void delete(UserDto user);
}
