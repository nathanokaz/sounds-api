package com.projects.sounds_api.domain.users.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginData(

        @NotBlank
        String username,
        @NotBlank
        String password) {

}
