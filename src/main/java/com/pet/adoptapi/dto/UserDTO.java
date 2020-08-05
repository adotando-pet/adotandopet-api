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
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirmation;


}
