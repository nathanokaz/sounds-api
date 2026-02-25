package com.projects.sounds_api.domain.music.dto;

import com.projects.sounds_api.domain.music.Music;

public record MusicRegisterDetails(Long id, String name, String artist, MusicGender gender, Integer durationInSeconds) {

    public MusicRegisterDetails(Music music) {
        this(music.getId(), music.getName(), music.getArtist(), music.getGender(), music.getDurationInSeconds());
    }

}
