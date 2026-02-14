package com.projects.sounds_api.domain.playlists.repository;

import com.projects.sounds_api.domain.playlists.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
