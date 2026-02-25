package com.projects.sounds_api.domain.playlist.service;

import com.projects.sounds_api.domain.music.repository.MusicRepository;
import com.projects.sounds_api.domain.playlist.Playlist;
import com.projects.sounds_api.domain.playlist.dto.*;
import com.projects.sounds_api.domain.playlist.repository.PlaylistRepository;
import com.projects.sounds_api.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Transactional
    public Playlist createPlaylist(CreatePlaylistData data) {
        var playlist = new Playlist(data);
        var user = getUser();
        playlist.setUser(user);
        playlist.setCreator(user.getUsername());
        playlistRepository.save(playlist);
        return playlist;
    }

    public Page<PlaylistDetails> showPlaylists(Pageable pageable) {
        var page = playlistRepository.findByUserId(getUser().getId(), pageable).map(playlist -> new PlaylistDetails(playlist));
        return page;
    }

    public Playlist showPlaylistById(Long id) {
        var playlist = playlistRepository.findByIdAndUserId(id, getUser().getId());
        if (playlist.isPresent()) {
            return playlist.get();
        } else {
            throw new EntityNotFoundException("playlist id is invalid");
        }
    }

    @Transactional
    public Playlist editPlaylistData(EditPlaylistData data) {
        var playlist = verifyIfExists(data.id());
        playlist.updateData(data);
        return playlist;
    }

    @Transactional
    public void insertMusicToPlaylist(InsertMusicData data) {
        var playlist = verifyIfExists(data.id());
        var musics = musicRepository.findAllById(data.musicId());
        if (musics.size() != data.musicId().size()) {
            throw new EntityNotFoundException("some music ids are invalid");
        } else {
            playlist.getMusic().addAll(musics);
            playlistRepository.save(playlist);
        }
    }

    @Transactional
    public void deletePlaylist(Long id) {
        verifyIfExists(id);
        playlistRepository.deleteById(id);
    }

    @Transactional
    public void deleteMusicFromPlaylist(DeleteMusicFromPlaylistData data) {
        var playlist = verifyIfExists(data.playlistId());
        var music = musicRepository.findById(data.musicId());
        if (music.isPresent()) {
            playlist.getMusic().remove(music.get());
            playlistRepository.save(playlist);
        } else {
            throw new EntityNotFoundException("music id is invalid");
        }
    }

    public Playlist verifyIfExists(Long id) {
        var playlistExists = playlistRepository.findById(id);
        if (playlistExists.isPresent()) {
             return playlistExists.get();
        } else {
            throw new EntityNotFoundException("playlist not found");
        }
    }

    private User getUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

}
