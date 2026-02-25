package com.projects.sounds_api.domain.admin.services;

import com.projects.sounds_api.domain.playlist.Playlist;
import com.projects.sounds_api.domain.playlist.dto.EditPlaylistData;
import com.projects.sounds_api.domain.playlist.dto.PlaylistDetails;
import com.projects.sounds_api.domain.playlist.repository.PlaylistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlaylistAdminControllerService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public Page<PlaylistDetails> showPlaylists(Pageable pageable) {
        var page = playlistRepository.findAll(pageable).map(playlist -> new PlaylistDetails(playlist));
        return page;
    }

    @Transactional
    public void deletePlaylist(Long id) {
        verifyIfExists(id);
        playlistRepository.deleteById(id);
    }

    @Transactional
    public Playlist editPlaylist(EditPlaylistData data) {
        var playlist = verifyIfExists(data.id());
        playlist.updateData(data);
        return playlist;
    }

    public Playlist showPlaylistsFromUser(Long id) {
        var playlist = verifyIfExists(id);
        return playlist;
    }

    public Playlist verifyIfExists(Long id) {
        var playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return playlist.get();
        } else {
            throw new EntityNotFoundException("some playlist ids are invalid");
        }
    }

}
