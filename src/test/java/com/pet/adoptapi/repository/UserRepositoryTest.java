package com.pet.adoptapi.repository;

import com.pet.adoptapi.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    @DisplayName("[SAVE] - Should save a user")
    public void saveNewUser(){
        User user = createUserValid();

        User savedUser = repository.save(user);

        assertThat(savedUser.getId()).isNotNull();

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
    @DisplayName("[SAVE] - Should return false if not already exists email User ")
    public void saveReturnFalseExistsEmail(){
        String email = "phelliperodrigues.dev@gmail.com";

        boolean exists = repository.existsByEmail(email);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("[SAVE] - Should return true if already exists email User ")
    public void saveReturnTrueIfAlreadyExistsEmail(){
        String email = "phelliperodrigues.dev@gmail.com";

        User user = createUserValid();
        entityManager.persist(user);

        boolean exists = repository.existsByEmail(email);

        assertThat(exists).isTrue();
    }
}
