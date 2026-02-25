package com.projects.sounds_api.domain.music.dto;

import com.projects.sounds_api.domain.music.Music;

public record MusicUpdateDetails(Long id, String name, String artist, MusicGender gender, Integer durationInSeconds) {

    public MusicUpdateDetails(Music music) {
        this(music.getId(), music.getName(), music.getArtist(), music.getGender(), music.getDurationInSeconds());
    }

}
