package com.projects.sounds_api.domain.musics.repository;

import com.projects.sounds_api.domain.musics.Music;
import com.projects.sounds_api.domain.playlists.Playlist;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
