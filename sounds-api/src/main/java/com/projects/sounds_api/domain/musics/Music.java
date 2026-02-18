package com.projects.sounds_api.domain.musics;

import com.projects.sounds_api.domain.musics.dto.EditMusicData;
import com.projects.sounds_api.domain.musics.dto.MusicGender;
import com.projects.sounds_api.domain.musics.dto.MusicRegisterData;
import com.projects.sounds_api.domain.playlists.Playlist;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "musics")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String artist;

    @Enumerated(EnumType.STRING)
    private MusicGender gender;

    @Column(nullable = false)
    private Integer durationInSeconds;

    @ManyToMany(mappedBy = "musics", fetch = FetchType.LAZY)
    private List<Playlist> playlists = new ArrayList<>();

    public Music(@Valid MusicRegisterData data) {
        this.name = data.name();
        this.artist = data.artist();
        this.gender = data.gender();
        this.durationInSeconds = data.durationInSeconds();
    }

    public void updateData(@Valid EditMusicData data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.artist() != null) {
            this.artist = data.artist();
        }
        if (data.gender() != null) {
            this.gender = data.gender();
        }
        if (durationInSeconds != null) {
            this.durationInSeconds = data.durationInSeconds();
        }
    }
}
