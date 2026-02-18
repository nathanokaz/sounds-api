package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.musics.repository.MusicRepository;
import com.projects.sounds_api.domain.playlists.Playlist;
import com.projects.sounds_api.domain.playlists.dto.*;
import com.projects.sounds_api.domain.playlists.repository.PlaylistRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/playlist")
@SecurityRequirement(name = "bearer-key")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<PlaylistCreateDetails> createPlaylist(@RequestBody @Valid CreatePlaylistData data, UriComponentsBuilder uriComponentsBuilder) {
        var playlist = new Playlist(data);
        playlistRepository.save(playlist);
        var uri = uriComponentsBuilder.path("/playlist/create/{id}").buildAndExpand(playlist.getId()).toUri();
        return ResponseEntity.created(uri).body(new PlaylistCreateDetails(playlist));
    }

    @GetMapping("/show")
    public ResponseEntity<Page<PlaylistDetails>> showPlaylists(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        var page = playlistRepository.findAll(pageable).map(playlist -> new PlaylistDetails(playlist));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<PlaylistDetails> showPlaylistsById(@PathVariable Long id) {
        var playlistExists = playlistRepository.findById(id);
        if (playlistExists.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            var playlist = playlistExists.get();
            return ResponseEntity.ok(new PlaylistDetails(playlist));
        }
    }

    @PutMapping("/edit")
    @Transactional
    public ResponseEntity<PlaylistUpdateDetails> editPlaylist(@RequestBody @Valid EditPlaylistData data) {
        var playlistExists = playlistRepository.findById(data.id());
        if (playlistExists.isPresent()) {
            var playlist = playlistExists.get();
            playlist.updateData(data);
            return ResponseEntity.ok(new PlaylistUpdateDetails(data));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insert/musics")
    @Transactional
    public ResponseEntity<?> insertMusicToPlaylist(@RequestBody @Valid InsertMusicData data) {
        var playlistExists = playlistRepository.findById(data.id());
        if (playlistExists.isEmpty()) {
            return ResponseEntity.badRequest().body("the playlist id is invalid");
        } else {
            var playlist = playlistExists.get();
            var musics = musicRepository.findAllById(data.musicId());
            if (musics.size() != data.musicId().size()) {
                return ResponseEntity.badRequest().body("some music id are invalid");
            } else {
                playlist.getMusics().addAll(musics);
                playlistRepository.save(playlist);
                return ResponseEntity.ok().build();
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        var playlistExists = playlistRepository.findById(id);
        if (playlistExists.isPresent()) {
            playlistRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/music")
    @Transactional
    public ResponseEntity<?> deleteMusicFromPlaylist(@RequestBody @Valid DeleteMusicFromPlaylistData data) {
        var playlistExists = playlistRepository.findById(data.playlistId());
        if (playlistExists.isPresent()) {
            var musicExists = musicRepository.findById(data.musicId());
            if (musicExists.isPresent()) {
                var playlist = playlistExists.get();
                var music = musicExists.get();
                playlist.getMusics().remove(music);
                playlistRepository.save(playlist);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
