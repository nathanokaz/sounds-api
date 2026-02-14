package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.users.User;
import com.projects.sounds_api.domain.users.dto.UserLoginData;
import com.projects.sounds_api.domain.users.dto.UserRegisterData;
import com.projects.sounds_api.domain.users.dto.UserRegisterDetails;
import com.projects.sounds_api.domain.users.repository.UserRepository;
import com.projects.sounds_api.infra.security.dto.TokenDetails;
import com.projects.sounds_api.infra.security.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDetails> login(@RequestBody @Valid UserLoginData data) {
        var user = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(user);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDetails(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDetails> register(@RequestBody @Valid UserRegisterData data, UriComponentsBuilder uriComponentsBuilder) {
        if (userRepository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().build();
        } else {
            var password = new BCryptPasswordEncoder().encode(data.password());
            var user = new User(null, data.username(), password, data.role());
            userRepository.save(user);
            var uri = uriComponentsBuilder.path("/auth/register/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(new UserRegisterDetails(user));
        }
    }

}
