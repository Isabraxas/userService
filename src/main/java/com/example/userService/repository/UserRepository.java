package com.example.userService.repository;

import com.example.userService.domain.UserModel;
import com.example.userService.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserModel,Long> {

    @Transactional
    void deleteByUserCode(String userCode);

    Page<UserModel> findAllByOrderByUserId(Pageable pageable);

    UserModel findByUserCode(String userCode);

    UserModel findByUserName(String userName);
}
