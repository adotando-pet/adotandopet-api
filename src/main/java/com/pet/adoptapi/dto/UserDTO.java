package com.pet.adoptapi.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty(message = "name is required")
    private String name;
    @NotEmpty(message = "email is required")
    private String email;
    @NotEmpty(message = "password is required")
    private String password;
    @NotEmpty(message = "passwordConfirmation is required")
    private String passwordConfirmation;


}
