package com.projects.sounds_api.domain.playlist.dto;

public record PlaylistUpdateDetails(Long id, String name) {

    public PlaylistUpdateDetails(EditPlaylistData data) {
        this(data.id(), data.name());
    }

}
