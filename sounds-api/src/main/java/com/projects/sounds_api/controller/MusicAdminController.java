package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.admin.services.MusicAdminControllerService;
import com.projects.sounds_api.domain.music.dto.EditMusicData;
import com.projects.sounds_api.domain.music.dto.MusicDetails;
import com.projects.sounds_api.domain.music.dto.MusicUpdateDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music/admin")
@SecurityRequirement(name = "bearer-key")
public class MusicAdminController {

    @Autowired
    private MusicAdminControllerService musicAdminControllerService;

    @GetMapping("/show")
    public ResponseEntity<Page<MusicDetails>> showMusics(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
       var page = musicAdminControllerService.showMusics(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<MusicDetails> showMusicById(@PathVariable Long id) {
        var music = musicAdminControllerService.showMusicById(id);
        return ResponseEntity.ok(new MusicDetails(music));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMusic(@PathVariable Long id) {
        musicAdminControllerService.deleteMusic(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<MusicUpdateDetails> editMusic(@RequestBody @Valid EditMusicData data) {
       var music = musicAdminControllerService.editMusic(data);
        return ResponseEntity.ok(new MusicUpdateDetails(music));
    }

}
