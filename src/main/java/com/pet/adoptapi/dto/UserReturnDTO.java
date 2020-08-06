package com.pet.adoptapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReturnDTO {
    private Long id;

    private String name;
    private String email;

}
