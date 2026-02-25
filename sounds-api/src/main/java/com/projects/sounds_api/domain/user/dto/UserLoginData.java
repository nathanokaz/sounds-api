package com.projects.sounds_api.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginData(

        @NotBlank(message = "invalid username")
        String username,
        @NotBlank(message = "invalid password")
        String password) {

}
