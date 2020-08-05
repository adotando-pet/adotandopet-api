package com.pet.adoptapi.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pet.adoptapi.dto.UserDTO;
import com.pet.adoptapi.model.User;
import com.pet.adoptapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public UserDTO create(@RequestBody UserDTO dto) {

        User entity = mapper.map(dto, User.class);
        entity = service.save(entity);
        return mapper.map(entity, UserDTO.class);
    }
}
