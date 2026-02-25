package com.projects.sounds_api.domain.admin.services;

import com.projects.sounds_api.domain.music.Music;
import com.projects.sounds_api.domain.music.dto.EditMusicData;
import com.projects.sounds_api.domain.music.dto.MusicDetails;
import com.projects.sounds_api.domain.music.repository.MusicRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MusicAdminControllerService {

    @Autowired
    private MusicRepository musicRepository;

    public Page<MusicDetails> showMusics(Pageable pageable) {
        var page = musicRepository.findAll(pageable).map(music -> new MusicDetails(music));
        return page;
    }

    public Music showMusicById(Long id) {
        return verifyIfExists(id);
    }

    @Transactional
    public void deleteMusic(Long id) {
        verifyIfExists(id);
        musicRepository.deleteById(id);
    }

    @Transactional
    public Music editMusic(EditMusicData data) {
        var music = verifyIfExists(data.id());
        music.updateData(data);
        return music;
    }

    public Music verifyIfExists(Long id) {
        var music = musicRepository.findById(id);
        if (music.isPresent()) {
            return music.get();
        } else {
            throw new EntityNotFoundException("some music ids are invalid");
        }
    }

}
