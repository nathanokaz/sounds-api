package com.projects.sounds_api.domain.playlists.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePlaylistData(

        @NotBlank(message = "invalid name")
        String name,
        @NotBlank(message = "invalid creator")
        String creator) {
}
