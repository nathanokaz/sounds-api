package com.projects.sounds_api.infra.security.services;

import com.projects.sounds_api.domain.user.User;
import com.projects.sounds_api.domain.user.dto.UserLoginData;
import com.projects.sounds_api.domain.user.dto.UserRegisterData;
import com.projects.sounds_api.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String login(UserLoginData data) {
        var user = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(user);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    @Transactional
    public User register(UserRegisterData data) throws BadRequestException {
        if (userRepository.findByUsername(data.username()) != null) {
            throw new BadRequestException("username already exists");
        } else {
            var password = new BCryptPasswordEncoder().encode(data.password());
            var user = new User(data.username(), password, data.role());
            userRepository.save(user);
            return user;
        }
    }

}
