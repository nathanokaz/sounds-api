package com.projects.sounds_api.domain.playlists.dto;

import jakarta.validation.Valid;

public record PlaylistUpdateDetails(@Valid EditPlaylistData data) {
}
