package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.music.dto.*;
import com.projects.sounds_api.domain.music.service.MusicService;
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
@RequestMapping("/music")
@SecurityRequirement(name = "bearer-key")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @PostMapping("/register")
    public ResponseEntity<MusicRegisterDetails> registerMusic(@RequestBody @Valid MusicRegisterData data, UriComponentsBuilder uriComponentsBuilder) {
        var music = musicService.registerMusic(data);
        var uri = uriComponentsBuilder.path("/music/register/{id}").buildAndExpand(music.getId()).toUri();
        return ResponseEntity.created(uri).body(new MusicRegisterDetails(music));
    }

    @GetMapping("/show")
    public ResponseEntity<Page<MusicDetails>> showMusics(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        var page = musicService.showMusics(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showMusicsById(@PathVariable Long id) {
        var music = musicService.showMusicByid(id);
        return ResponseEntity.ok(new MusicDetails(music));
    }

    @PutMapping("/edit")
    public ResponseEntity<MusicUpdateDetails> editMusic(@RequestBody @Valid EditMusicData data) {
        var music = musicService.updateData(data);
        return ResponseEntity.ok(new MusicUpdateDetails(music));
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteMusic(@PathVariable Long id) {
        musicService.deleteMusic(id);
        return ResponseEntity.noContent().build();
    }

}
