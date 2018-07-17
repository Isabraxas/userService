package com.example.userService.controller;


import com.example.userService.domain.UserModel;
import com.example.userService.dto.UserDto;
import com.example.userService.error.ConflictsException;
import com.example.userService.error.EntidadError;
import com.example.userService.error.NoEncontradoRestException;
import com.example.userService.repository.UserRepository;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Value("${spring.datasource.url}")
    private String datasource;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${server.port}")
    private String port;
    @Value("${config.git}")
    private String configGit;

    private static final String template = "Datasource: %s, Username: %s, Password: %s, PortApp: %s , ConfigGit: %s ";

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/info")
    public String getIntitialData() {
        String info= String.format(template,datasource,username, password, port, configGit);
        System.out.println("INFO: "+ info);
        return info;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll() {
        List<UserModel> userModelList= this.userRepository.findAll();

        List<UserDto> userDtoList = userModelList.stream().map(UserDto::new).collect(Collectors.toList());
        /*map.(temp -> {
            UserDto obj = new UserDto(temp);
            return obj;
        }).collect(Collectors.toList());*/
        return userDtoList;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
       //TODO encryptar la contraseña antes de guardar

        try {
            userDto=this.userService.save(userDto);
            //userDto.setUserId(userModel.getUserId());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable Long id){
        UserDto user = this.userService.getById(id);
        
        return user;
    }

    @GetMapping(value = "/userCode/{userCode}")
    public UserDto getUserByCode(@PathVariable String userCode){

        UserDto user = this.userService.getByUserCode(userCode);
        return user;
    }


    @PutMapping
    public UserDto updateUser(@RequestBody @Valid UserDto user){
        return this.userService.update(user);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteUser(@RequestBody UserDto user){
        this.userService.delete(user);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoEncontradoRestException.class)
    public EntidadError handleNotFound(NoEncontradoRestException exception){
        EntidadError error = new EntidadError();
        ////error.setId(exception.getErrorDetalle().getId());
        error.setEstado("error");
        error.setError(exception.getErrorDetalle());
        return error;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictsException.class)
    public EntidadError handleConflict(ConflictsException exception){
        EntidadError error = new EntidadError();
        ////error.setId(exception.getErrorDetalle().getId());
        error.setEstado("error");
        error.setError(exception.getErrorDetalle());
        return error;
    }

}
