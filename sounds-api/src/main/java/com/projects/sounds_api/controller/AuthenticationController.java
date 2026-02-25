package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.user.dto.UserLoginData;
import com.projects.sounds_api.domain.user.dto.UserRegisterData;
import com.projects.sounds_api.domain.user.dto.UserRegisterDetails;
import com.projects.sounds_api.infra.security.dto.TokenDetails;
import com.projects.sounds_api.infra.security.services.AuthenticationService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDetails> login(@RequestBody @Valid UserLoginData data) {
        var token = authenticationService.login(data);
        return ResponseEntity.ok(new TokenDetails(token));
    }

    @PostMapping("/register")
    //apenas para fins de simulação
    public ResponseEntity<UserRegisterDetails> register(@RequestBody @Valid UserRegisterData data, UriComponentsBuilder uriComponentsBuilder) throws BadRequestException {
        var user = authenticationService.register(data);
        var uri = uriComponentsBuilder.path("/auth/register/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserRegisterDetails(user));
    }

}
