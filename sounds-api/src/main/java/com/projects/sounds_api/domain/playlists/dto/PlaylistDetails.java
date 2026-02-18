package com.projects.sounds_api.domain.playlists.dto;

import com.projects.sounds_api.domain.playlists.Playlist;

public record PlaylistDetails(Long id, String name, String creator) {

    public PlaylistDetails(Playlist playlist) {
        this(playlist.getId(), playlist.getName(), playlist.getCreator());
    }

}
