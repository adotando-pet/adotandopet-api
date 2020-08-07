package com.pet.adoptapi.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pet.adoptapi.dto.UserDTO;
import com.pet.adoptapi.dto.UserReturnDTO;
import com.pet.adoptapi.exception.BusinessException;
import com.pet.adoptapi.exception.api.ApiErrors;
import com.pet.adoptapi.model.User;
import com.pet.adoptapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    private ModelMapper mapper;

    public UserController(UserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public UserReturnDTO create(@Valid @RequestBody UserDTO dto) {

        User entity = mapper.map(dto, User.class);
        entity = service.save(entity);
        return mapper.map(entity, UserReturnDTO.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);
    }
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException ex){
        return new ApiErrors(ex);
    }
}
