package com.projects.sounds_api.domain.playlist.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePlaylistData(

        @NotBlank(message = "invalid name")
        String name) {
}
