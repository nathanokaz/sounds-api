package com.projects.sounds_api.domain.music.service;

import com.projects.sounds_api.domain.music.Music;
import com.projects.sounds_api.domain.music.dto.EditMusicData;
import com.projects.sounds_api.domain.music.dto.MusicDetails;
import com.projects.sounds_api.domain.music.dto.MusicRegisterData;
import com.projects.sounds_api.domain.music.repository.MusicRepository;
import com.projects.sounds_api.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Transactional
    public Music registerMusic(MusicRegisterData data) {
        var music = new Music(data);
        var user = getUser();
        music.setUser(user);
        musicRepository.save(music);
        return music;
    }

    public Page<MusicDetails> showMusics(Pageable pageable) {
        var page = musicRepository.findByUserId(getUser().getId() ,pageable).map(music -> new MusicDetails(music));
        return page;
    }

    public Music showMusicByid(Long id) {
        var music = musicRepository.findByIdAndUserId(id, getUser().getId());
        if (music.isPresent()) {
            return music.get();
        } else {
            throw new EntityNotFoundException("music id is invalid");
        }
    }

    @Transactional
    public Music updateData(EditMusicData data) {
        var music = verifyIfExists(data.id());
        music.updateData(data);
        return music;
    }

    @Transactional
    public void deleteMusic(Long id) {
        verifyIfExists(id);
        musicRepository.deleteById(id);
    }

    public Music verifyIfExists(Long id) {
        var musicExists = musicRepository.findById(id);
        if (musicExists.isPresent()) {
            return musicExists.get();
        } else {
           throw  new EntityNotFoundException("music not found");
        }
    }

    private User getUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

}
