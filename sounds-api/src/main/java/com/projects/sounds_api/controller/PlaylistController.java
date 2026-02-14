package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.musics.repository.MusicRepository;
import com.projects.sounds_api.domain.playlists.Playlist;
import com.projects.sounds_api.domain.playlists.dto.CreatePlaylistData;
import com.projects.sounds_api.domain.playlists.dto.InsertMusicData;
import com.projects.sounds_api.domain.playlists.dto.PlaylistCreateDetails;
import com.projects.sounds_api.domain.playlists.repository.PlaylistRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;

    @PostMapping("/create")
    public ResponseEntity<PlaylistCreateDetails> create(@RequestBody @Valid CreatePlaylistData data, UriComponentsBuilder uriComponentsBuilder) {
        var playlist = new Playlist(data);
        playlistRepository.save(playlist);
        var uri = uriComponentsBuilder.path("/playlist/create/{id}").buildAndExpand(playlist.getId()).toUri();
        return ResponseEntity.created(uri).body(new PlaylistCreateDetails(playlist));
    }

    @PostMapping("/insert/musics")
    public ResponseEntity<String> insert(@RequestBody @Valid InsertMusicData data) {
        //playlistRepository retorna um optional, por isso não se pode fazer: if (playlistRepository.findById(data.id() == null)
        var playlistExists = playlistRepository.findById(data.id());
        if (playlistExists.isEmpty()) {
            return ResponseEntity.badRequest().body("the playlist id is invalid");
        } else {
            //pegamos a entidade playlist com .get() para manipula-la
            var playlist = playlistExists.get();
            var musics = musicRepository.findAllById(data.musicId());
            if (musics.size() != data.musicId().size()) {
                return ResponseEntity.badRequest().body("some music id are invalid");
            } else {
                //playlist.getMusics() é a lista de musicas dentro da playlist
                playlist.getMusics().addAll(musics);
                playlistRepository.save(playlist);
                return ResponseEntity.ok().build();
            }
        }
    }

}
