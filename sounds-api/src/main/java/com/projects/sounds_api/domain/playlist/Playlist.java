package com.projects.sounds_api.domain.playlist;

import com.projects.sounds_api.domain.music.Music;
import com.projects.sounds_api.domain.playlist.dto.CreatePlaylistData;
import com.projects.sounds_api.domain.playlist.dto.EditPlaylistData;
import com.projects.sounds_api.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String creator;

    @ManyToMany
    @JoinTable(name = "tb_music_playlist",
    joinColumns = @JoinColumn(name = "playlist_id"),
    inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> music = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Playlist(@Valid CreatePlaylistData data) {
        this.name = data.name();
    }

    public void updateData(@Valid EditPlaylistData data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.creator() != null) {
            this.creator = data.creator();
        }
    }
}
