package com.pet.adoptapi.service;

import com.pet.adoptapi.exception.BusinessException;
import com.pet.adoptapi.model.User;
import com.pet.adoptapi.repository.UserRepository;
import com.pet.adoptapi.service.imp.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    UserService service;

    @MockBean
    private UserRepository repository;

    @BeforeEach
    public void setUp() {this.service = new UserServiceImp(repository);  }

    @Test
    @DisplayName("[SAVE] - Should save a user")
    public void save() {
        User user = createUserValid();

        when(repository.existsByEmail(anyString())).thenReturn(false);

        when(repository.save(user))
                .thenReturn(User.builder().id(1L)
                        .email("phelliperodrigues.dev@gmail.com")
                        .name("Phellipe Rodrigues")
                        .password("123456")
                        .passwordConfirmation("123456")
                        .build());

        User savedUser = service.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Phellipe Rodrigues");
        assertThat(savedUser.getEmail()).isEqualTo("phelliperodrigues.dev@gmail.com");
    }

    private User createUserValid() {
        return User.builder()
                .email("phelliperodrigues.dev@gmail.com")
                .name("Phellipe Rodrigues")
                .password("123456")
                .passwordConfirmation("123456")
                .build();
    }

    @Test
    @DisplayName("[SAVE] - Should show exception User already email")
    public void dontSaveDuplicateUser(){
        String messageError = "Email já cadastrado.";

        User user = createUserValid();
        when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        Throwable exception = catchThrowable(() -> service.save(user));

        assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage(messageError);

        Mockito.verify(repository, Mockito.never()).save(user);
    }


}
