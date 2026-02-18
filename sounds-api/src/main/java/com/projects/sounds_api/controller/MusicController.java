package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.musics.Music;
import com.projects.sounds_api.domain.musics.dto.*;
import com.projects.sounds_api.domain.musics.repository.MusicRepository;
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
    private MusicRepository musicRepository;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<MusicRegisterDetails> registerMusic(@RequestBody @Valid MusicRegisterData data, UriComponentsBuilder uriComponentsBuilder) {
        var music = new Music(data);
        musicRepository.save(music);
        var uri = uriComponentsBuilder.path("/music/register/{id}").buildAndExpand(music.getId()).toUri();
        return ResponseEntity.created(uri).body(new MusicRegisterDetails(music));
    }

    @GetMapping("/show")
    public ResponseEntity<Page<MusicDetails>> showMusics(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        var page = musicRepository.findAll(pageable).map(music -> new MusicDetails(music));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showMusicsById(@PathVariable Long id) {
        var musicExists = musicRepository.findById(id);
        if (musicExists.isPresent()) {
            var music = musicExists.get();
            return ResponseEntity.ok(new MusicDetails(music));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit")
    @Transactional
    public ResponseEntity<MusicUpdateDetails> editMusic(@RequestBody @Valid EditMusicData data) {
        var musicExists = musicRepository.findById(data.id());
        if (musicExists.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            var music = musicExists.get();
            music.updateData(data);
            return ResponseEntity.ok(new MusicUpdateDetails(music));
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteMusic(@PathVariable Long id) {
        var music = musicRepository.findById(id);
        if (music.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            musicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

}
