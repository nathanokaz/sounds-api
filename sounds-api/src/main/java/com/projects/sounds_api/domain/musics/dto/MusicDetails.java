package com.projects.sounds_api.domain.musics.dto;

import com.projects.sounds_api.domain.musics.Music;

public record MusicDetails(Long id, String name, String artist, MusicGender gender, Integer durationInSeconds) {

    public MusicDetails(Music music) {
        this(music.getId(), music.getName(), music.getArtist(), music.getGender(), music.getDurationInSeconds());
    }

}
