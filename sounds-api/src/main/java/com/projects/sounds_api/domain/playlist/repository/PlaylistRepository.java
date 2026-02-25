package com.projects.sounds_api.domain.playlist.repository;

import com.projects.sounds_api.domain.playlist.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

   Page<Playlist> findByUserId(Long id, Pageable pageable);

   Optional<Playlist> findByIdAndUserId(Long id, Long userId);

}

