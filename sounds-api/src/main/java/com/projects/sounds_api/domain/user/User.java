package com.projects.sounds_api.domain.user;

import com.projects.sounds_api.domain.admin.dto.AlterRoleData;
import com.projects.sounds_api.domain.music.Music;
import com.projects.sounds_api.domain.playlist.Playlist;
import com.projects.sounds_api.domain.user.dto.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Music> music = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists = new ArrayList<>();

    @Column
    private Boolean account;

    public User(@NotBlank(message = "invalid username") String username, String password, @NotNull(message = "invalid role") UserRoles role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.account = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRoles.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void alterRole(@Valid AlterRoleData data) {
        this.role = data.role();
    }

    public void disableAccount() {
        this.account = false;
    }

    public void activateAccount() {
        this.account = true;
    }

    public void updateData(@NotBlank(message = "invalid username") String username, String password) {
        if (username != null) {
            this.username = username;
        }
        if (password != null) {
            this.password = password;
        }
    }
}
