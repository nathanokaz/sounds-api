package com.projects.sounds_api.domain.admin.dto;

import com.projects.sounds_api.domain.user.User;
import com.projects.sounds_api.domain.user.dto.UserRoles;

public record UserDetails(Long id, String username, String password, UserRoles role) {

    public UserDetails(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

}
