package com.projects.sounds_api.domain.user.services;

import com.projects.sounds_api.domain.user.User;
import com.projects.sounds_api.domain.user.dto.UserEditAccount;
import com.projects.sounds_api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserControllerService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User editAccount(UserEditAccount data) {
        var user = verifyIfExists(data.id());
        if (user.getUsername().equals(getUser().getUsername())) {
            var password = new BCryptPasswordEncoder().encode(data.password());
            user.updateData(data.username(), password);
            return user;
        } else {
            throw new EntityNotFoundException("invalid id");
        }
    }

    @Transactional
    public void deleteAccount(Long id) {
        var user = verifyIfExists(id);
        if (user.getId().equals(getUser().getId())) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("invalid id");
        }
    }

    public User verifyIfExists(Long id) {
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("some user id are invalid");
        }
    }

    private User getUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

}
