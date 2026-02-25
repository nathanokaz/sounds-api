package com.projects.sounds_api.domain.user.dto;

import com.projects.sounds_api.domain.user.User;

public record UserRegisterDetails(Long id, String username) {

    public UserRegisterDetails(User user) {
        this(user.getId(), user.getUsername());
    }

}
