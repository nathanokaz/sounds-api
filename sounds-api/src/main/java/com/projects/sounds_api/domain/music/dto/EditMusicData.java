package com.projects.sounds_api.domain.music.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EditMusicData(

        @NotNull(message = "invalid id")
        @Min(1)
        Long id,
        String name,
        String artist,
        MusicGender gender,
        Integer durationInSeconds) {

}
