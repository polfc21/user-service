package com.gymbo.userservice.infrastructure.controller.dto.request;

import com.gymbo.userservice.infrastructure.controller.dto.validator.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @ValidEmail
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private LocalDate birthDate;
}
