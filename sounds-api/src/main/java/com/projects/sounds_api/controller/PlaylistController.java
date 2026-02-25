package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.playlist.dto.*;
import com.projects.sounds_api.domain.playlist.service.PlaylistService;
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
    private PlaylistService playlistService;

    @PostMapping("/create")
    public ResponseEntity<PlaylistCreateDetails> createPlaylist(@RequestBody @Valid CreatePlaylistData data, UriComponentsBuilder uriComponentsBuilder) {
        var playlist = playlistService.createPlaylist(data);
        var uri = uriComponentsBuilder.path("/playlist/create/{id}").buildAndExpand(playlist.getId()).toUri();
        return ResponseEntity.created(uri).body(new PlaylistCreateDetails(playlist));
    }

    @GetMapping("/show")
    public ResponseEntity<Page<PlaylistDetails>> showPlaylists(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        var page = playlistService.showPlaylists(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<PlaylistDetails> showPlaylistsById(@PathVariable Long id) {
        var playlist = playlistService.showPlaylistById(id);
        return ResponseEntity.ok(new PlaylistDetails(playlist));
    }

    @PutMapping("/edit")
    public ResponseEntity<PlaylistUpdateDetails> editPlaylist(@RequestBody @Valid EditPlaylistData data) {
        var playlist = playlistService.editPlaylistData(data);
        return ResponseEntity.ok(new PlaylistUpdateDetails(data));
    }

    @PostMapping("/insert/music")
    public ResponseEntity<?> insertMusicToPlaylist(@RequestBody @Valid InsertMusicData data) {
        playlistService.insertMusicToPlaylist(data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/music")
    @Transactional
    public ResponseEntity<?> deleteMusicFromPlaylist(@RequestBody @Valid DeleteMusicFromPlaylistData data) {
        playlistService.deleteMusicFromPlaylist(data);
        return ResponseEntity.noContent().build();
    }

}
