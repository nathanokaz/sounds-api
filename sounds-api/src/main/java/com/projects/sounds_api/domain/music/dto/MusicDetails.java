package com.projects.sounds_api.domain.music.dto;

import com.projects.sounds_api.domain.music.Music;

public record MusicDetails(Long id, String name, String artist, MusicGender gender, Integer durationInSeconds) {

    public MusicDetails(Music music) {
        this(music.getId(), music.getName(), music.getArtist(), music.getGender(), music.getDurationInSeconds());
    }

}
