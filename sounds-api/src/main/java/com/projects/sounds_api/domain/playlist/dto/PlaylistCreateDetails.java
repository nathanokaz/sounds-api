package com.projects.sounds_api.domain.playlist.dto;

import com.projects.sounds_api.domain.playlist.Playlist;

public record PlaylistCreateDetails(String name, String creator) {

    public PlaylistCreateDetails(Playlist playlist) {
        this(playlist.getName(), playlist.getCreator());
    }

}
