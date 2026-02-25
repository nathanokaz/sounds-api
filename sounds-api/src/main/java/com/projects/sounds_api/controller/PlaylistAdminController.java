package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.admin.services.PlaylistAdminControllerService;
import com.projects.sounds_api.domain.playlist.dto.EditPlaylistData;
import com.projects.sounds_api.domain.playlist.dto.PlaylistDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist/admin")
@SecurityRequirement(name = "bearer-key")
public class PlaylistAdminController {

    @Autowired
    private PlaylistAdminControllerService playlistAdminControllerService;

    @GetMapping("/show")
    public ResponseEntity<Page<PlaylistDetails>> showPlaylists(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        var page = playlistAdminControllerService.showPlaylists(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        playlistAdminControllerService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editPlaylist(@RequestBody @Valid EditPlaylistData data) {
        var playlist = playlistAdminControllerService.editPlaylist(data);
        return ResponseEntity.ok(new PlaylistDetails(playlist));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<PlaylistDetails> showPlaylistsFromUser(@PathVariable Long id) {
        var playlist = playlistAdminControllerService.showPlaylistsFromUser(id);
        return ResponseEntity.ok(new PlaylistDetails(playlist));
    }

}
