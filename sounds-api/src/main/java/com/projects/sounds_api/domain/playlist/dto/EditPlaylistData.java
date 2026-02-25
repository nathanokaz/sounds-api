package com.projects.sounds_api.domain.playlist.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditPlaylistData(

        @NotNull
        @Min(1)
        Long id,
        @NotBlank(message = "invalid name")
        String name) {
}
