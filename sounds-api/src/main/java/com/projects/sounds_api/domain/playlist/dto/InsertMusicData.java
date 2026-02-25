package com.projects.sounds_api.domain.playlist.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record InsertMusicData(

        @NotNull(message = "playlist id is invalid")
        Long id,
        @NotEmpty(message = "ids can not be empty")
        List<Long> musicId) {
}
