package com.projects.sounds_api.domain.user.repository;

import com.projects.sounds_api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);

    Page<User> findByAccountTrue(Pageable pageable);
}
