package com.projects.sounds_api.domain.music.repository;

import com.projects.sounds_api.domain.music.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {

    Page<Music> findByUserId(Long id, Pageable pageable);

    Optional<Music> findByIdAndUserId(Long id, Long userId);

}
