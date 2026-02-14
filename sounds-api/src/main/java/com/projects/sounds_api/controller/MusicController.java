package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.musics.Music;
import com.projects.sounds_api.domain.musics.dto.MusicRegisterData;
import com.projects.sounds_api.domain.musics.dto.MusicRegisterDetails;
import com.projects.sounds_api.domain.musics.repository.MusicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicRepository musicRepository;

    @PostMapping("/register")
    public ResponseEntity<MusicRegisterDetails> register(@RequestBody @Valid MusicRegisterData data, UriComponentsBuilder uriComponentsBuilder) {
        var music = new Music(data);
        musicRepository.save(music);
        var uri = uriComponentsBuilder.path("/music/register/{id}").buildAndExpand(music.getId()).toUri();
        return ResponseEntity.created(uri).body(new MusicRegisterDetails(music));
    }

}
