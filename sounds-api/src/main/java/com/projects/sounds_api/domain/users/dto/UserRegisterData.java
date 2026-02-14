package com.projects.sounds_api.domain.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterData(

        @NotBlank(message = "invalid username")
        String username,
        @NotBlank(message = "invalid password")
        String password,
        @NotNull(message = "invalid role")
        UserRoles role) {

}
