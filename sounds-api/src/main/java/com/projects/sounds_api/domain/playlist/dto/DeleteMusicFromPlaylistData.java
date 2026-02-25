package com.projects.sounds_api.domain.playlist.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DeleteMusicFromPlaylistData(

        @NotNull
        @Min(1)
        Long playlistId,
        @NotNull
        @Min(1)
        Long musicId) {
}
