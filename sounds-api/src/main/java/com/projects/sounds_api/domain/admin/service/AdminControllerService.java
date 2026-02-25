package com.projects.sounds_api.domain.admin.service;

import com.projects.sounds_api.domain.admin.dto.AlterRoleData;
import com.projects.sounds_api.domain.admin.dto.UserDetails;
import com.projects.sounds_api.domain.user.User;
import com.projects.sounds_api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminControllerService {

    @Autowired
    private UserRepository userRepository;

    public Page<UserDetails> showUsers(Pageable pageable) {
        var page = userRepository.findByAccountTrue(pageable).map(user -> new UserDetails(user));
        return page;
    }

    public User showUserById(Long id) {
        return verifyIfExists(id);
    }

    @Transactional
    public User alterUserRole(AlterRoleData data) {
        var user = verifyIfExists(data.id());
        user.alterRole(data);
        return user;
    }

    @Transactional
    public void userAccountBan(Long id) {
        var user = verifyIfExists(id);
        user.disableAccount();
    }

    @Transactional
    public void userAccountReactivate(Long id) {
        var user = verifyIfExists(id);
        user.activateAccount();
    }

    public User verifyIfExists(Long id) {
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("user id is invalid");
        }
    }

}
