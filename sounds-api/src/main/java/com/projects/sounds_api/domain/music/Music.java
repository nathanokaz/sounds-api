package com.projects.sounds_api.domain.music;

import com.projects.sounds_api.domain.music.dto.EditMusicData;
import com.projects.sounds_api.domain.music.dto.MusicGender;
import com.projects.sounds_api.domain.music.dto.MusicRegisterData;
import com.projects.sounds_api.domain.playlist.Playlist;
import com.projects.sounds_api.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "musics")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
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

    @ManyToMany(mappedBy = "music", fetch = FetchType.LAZY)
    private List<Playlist> playlists = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
