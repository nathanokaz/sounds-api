package com.projects.sounds_api.domain.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserEditAccount(
        @NotNull
        @Min(1)
        Long id,
        @NotBlank(message = "invalid username")
        String username,
        @NotBlank(message = "invalid password")
        String password) {
}
