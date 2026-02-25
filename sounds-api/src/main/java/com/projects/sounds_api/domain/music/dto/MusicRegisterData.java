package com.projects.sounds_api.domain.music.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MusicRegisterData(

        @NotBlank(message = "name can not be empty")
        String name,
        @NotBlank(message = "artist can not be empty")
        String artist,
        @NotNull(message = "invalid gender")
        MusicGender gender,
        @NotNull(message = "invalid duration")
        Integer durationInSeconds) {
}
