package com.projects.sounds_api.domain.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterData(

        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotNull
        UserRoles role) {

}
