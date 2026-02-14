package com.projects.sounds_api.domain.playlists;

import com.projects.sounds_api.domain.musics.Music;
import com.projects.sounds_api.domain.playlists.dto.CreatePlaylistData;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
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
    private List<Music> musics = new ArrayList<>();

    public Playlist(@Valid CreatePlaylistData data) {
        this.name = data.name();
        this.creator = data.creator();
    }
}
