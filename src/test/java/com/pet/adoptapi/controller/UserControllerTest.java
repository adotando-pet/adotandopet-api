package com.pet.adoptapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.adoptapi.dto.UserDTO;
import com.pet.adoptapi.exception.BusinessException;
import com.pet.adoptapi.model.User;
import com.pet.adoptapi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String USER_API = "/users";
    @Autowired
    MockMvc mvc;

    @MockBean
    UserService service;
    
    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("[CREATE] - Should create an invalid user")
    public void createValidUserTest() throws Exception {
        UserDTO dto = createNewUserValid();

        User savedUser = User.builder()
                .id(1L)
                .name(createNewUserValid().getName())
                .email(createNewUserValid().getEmail())
                .password(createNewUserValid().getPassword())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        given(service.save(Mockito.any(User.class))).willReturn(savedUser);

        String json = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect( jsonPath("id").isNotEmpty())
                .andExpect( jsonPath("name").value(dto.getName()))
                .andExpect(jsonPath("email").value(dto.getEmail()));
    }



    @Test
    @DisplayName("[CREATE] - Should show exceptions in create an invalid user")
    public void createInvalidUserTest() throws Exception {

        String json = mapper.writeValueAsString(new UserDTO());

        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(4)));


    }

    @Test
    @DisplayName("[CREATE] - Should show message exceptions that name required")
    public void createInvalidUserWithoutNameTest() throws Exception {

        UserDTO dto =  UserDTO.builder()
                .email(createNewUserValid().getEmail())
                .password(createNewUserValid().getPassword())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        User savedUser = User.builder()
                .id(1L)
                .email(createNewUserValid().getEmail())
                .password(createNewUserValid().getPassword())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        given(service.save(Mockito.any(User.class))).willReturn(savedUser);

        String json = mapper.writeValueAsString(dto);
        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("name is required"));


    }

    @Test
    @DisplayName("[CREATE] - Should show message exceptions that email required")
    public void createInvalidUserWithoutEmailTest() throws Exception {

        UserDTO dto =  UserDTO.builder()
                .name(createNewUserValid().getName())
                .password(createNewUserValid().getPassword())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        User savedUser = User.builder()
                .id(1L)
                .name(createNewUserValid().getName())
                .password(createNewUserValid().getPassword())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        given(service.save(Mockito.any(User.class))).willReturn(savedUser);

        String json = mapper.writeValueAsString(dto);
        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("email is required"));


    }

    @Test
    @DisplayName("[CREATE] - Should show message exceptions that password required")
    public void createInvalidUserWithoutPasswordTest() throws Exception {

        UserDTO dto =  UserDTO.builder()
                .name(createNewUserValid().getName())
                .email(createNewUserValid().getEmail())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        User savedUser = User.builder()
                .id(1L)
                .name(createNewUserValid().getName())
                .email(createNewUserValid().getEmail())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        given(service.save(Mockito.any(User.class))).willReturn(savedUser);

        String json = mapper.writeValueAsString(dto);
        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("password is required"));


    }
    @Test
    @DisplayName("[CREATE] - Should show message exceptions that passwordConfirmation required")
    public void createInvalidUserWithoutPasswordConfirmationTest() throws Exception {

        UserDTO dto =  UserDTO.builder()
                .name(createNewUserValid().getName())
                .email(createNewUserValid().getEmail())
                .password(createNewUserValid().getPassword())
                .build();

        User savedUser = User.builder()
                .id(1L)
                .name(createNewUserValid().getName())
                .email(createNewUserValid().getEmail())
                .password(createNewUserValid().getPassword())
                .build();

        given(service.save(Mockito.any(User.class))).willReturn(savedUser);

        String json = mapper.writeValueAsString(dto);
        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("passwordConfirmation is required"));


    }

    @Test
    @DisplayName("[CREATE] - Should show exception with EMAIL duplicate")
    public void createValidUserWithEmailDuplicateTest() throws Exception{
        UserDTO dto = createNewUserValid();
        String json = mapper.writeValueAsString(dto);
        String messageError = "Email já cadastrado.";
        given(service.save(Mockito.any(User.class))).willThrow(new BusinessException(messageError));

        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(messageError));

    }

    @Test
    @DisplayName("[CREATE] - Should not return password and passwordConfirmation")
    public void dontReturnPasswordAndPasswordConfirmationTest() throws Exception {
        UserDTO dto = createNewUserValid();

        User savedUser = User.builder()
                .id(1L)
                .name(createNewUserValid().getName())
                .email(createNewUserValid().getEmail())
                .password(createNewUserValid().getPassword())
                .passwordConfirmation(createNewUserValid().getPasswordConfirmation())
                .build();

        given(service.save(Mockito.any(User.class))).willReturn(savedUser);

        String json = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder request = post(USER_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect( jsonPath("id").isNotEmpty())
                .andExpect( jsonPath("password").doesNotExist())
                .andExpect(jsonPath("passwordConfirmation").doesNotExist());
    }

    private UserDTO createNewUserValid() {
        return UserDTO.builder()
                .name("Phellipe Rodrigues")
                .email("phelliperodrigues.dev@gmail.com")
                .password("12345")
                .passwordConfirmation("12345")
                .build();
    }

}
