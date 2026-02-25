package com.projects.sounds_api.domain.playlist.dto;

import com.projects.sounds_api.domain.music.dto.MusicDetails;
import com.projects.sounds_api.domain.playlist.Playlist;

import java.util.List;

public record PlaylistDetails(Long id, String name, String creator, List<MusicDetails> musics) {

    public PlaylistDetails(Playlist playlist) {
        this(playlist.getId(), playlist.getName(), playlist.getCreator(), playlist.getMusic().stream().map(music -> new MusicDetails(music)).toList());
    }

}
