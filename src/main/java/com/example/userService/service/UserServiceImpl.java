package com.example.userService.service;


import com.example.userService.domain.UserModel;
import com.example.userService.dto.UserDto;
import com.example.userService.error.ConflictsException;
import com.example.userService.error.ErrorDetalle;
import com.example.userService.error.NoEncontradoRestException;
import com.example.userService.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    // logger
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDto getById(Long id) {
        UserModel userModel = this.userRepository.findOne(id);
        if(userModel == null) {
            String errorMsg = "El user con Id: "+ id +" no fue encontrado";
            throw new NoEncontradoRestException(errorMsg, new ErrorDetalle( "404",  errorMsg, "Hemos encontrado un error intentelo mas tarde"));
        }
        return new UserDto(userModel);
    }

    @Transactional
    @Override
    public UserDto getByUserName(String username) {
        UserModel userModel = this.userRepository.findByUserName(username);
        if(userModel == null){
            String errorMsg = "El user con UserName: "+ username +" no fue encontrado";
            throw new NoEncontradoRestException(errorMsg, new ErrorDetalle( "404", "El user con UserName: "+ username +" no fue encontrado", "Hemos encontrado un error intentelo mas tarde"));
        }else {
            return new UserDto(userModel);
        }

    }

    @Transactional
    @Override
    public UserDto getByUserCode(String code) {
        UserModel userModel = this.userRepository.findByUserCode(code);

        if(userModel == null) {
            String errorMsg = "El user con code: "+ code +" no fue encontrado";
            throw new NoEncontradoRestException(errorMsg, new ErrorDetalle( "404",  errorMsg, "Hemos encontrado un error intentelo mas tarde"));
        }
        return new UserDto(userModel);
    }

    @Transactional
    @Override
    public UserDto save(UserDto user) {
        log.info("Revisando si exite el user por number");
        UserModel userModel = this.userRepository.findByUserCode(user.getUserCode());
        UserModel userModel1 = this.userRepository.findByUserName(user.getUserName());

        if(userModel == null || userModel1 == null) {
            log.info("Creando user");


                user.setUserId(null);

                log.info("Almacenando  user");
                this.userRepository.save(new UserModel(user));

        }else{
            log.error("El user con number: "+ user.getUserCode() +" ya existe");
            String errorMsg = "El user con number: "+ user.getUserCode() +" ya existe";
            throw new ConflictsException(errorMsg, new ErrorDetalle("409","El user con number: "+ user.getUserCode() +" ya existe","Hemos encontrado un error intentelo nuevamente"));
        }
        return this.getByUserCode(user.getUserCode());
    }

    @Transactional
    @Override
    public List<UserDto> getAll(Pageable pageable) {
        Page<UserModel> userModelPage= this.userRepository.findAllByOrderByUserId(pageable);
        List<UserModel> userList = userModelPage.getContent();

        return userList.stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto update(UserDto user) {

        log.info("Revisando si exite el user por number");
        UserDto currentUser = this.getByUserCode(user.getUserCode());
        if(currentUser != null) {
            log.info("Actualizando user");
            user = this.actualizarEntityUser(currentUser , user);


                user.setUserId(currentUser.getUserId());

                log.info("Almacenando cambios");
                this.userRepository.save(new UserModel(user));
                return this.getByUserCode(user.getUserCode());

        }
        return null;
    }

    @Transactional
    @Override
    public void delete(UserDto user) {
        //this.userRepository.deleteById(user.getUserId());
        this.userRepository.deleteByUserCode(user.getUserCode());
    }

    public UserDto actualizarEntityUser(UserDto currentUser, UserDto newUser){
        if(newUser.getUserId() == null){
            newUser.setUserId(currentUser.getUserId());
        }

        if(newUser.getUserName() == null){
            newUser.setUserName(currentUser.getUserName());
        }
        if(newUser.getUserPassword() == null){
            newUser.setUserPassword(currentUser.getUserPassword());
        }
        if(newUser.getUserCreateTime() == null){
            newUser.setUserCreateTime(currentUser.getUserCreateTime());
        }
        return  newUser;
    }
}
