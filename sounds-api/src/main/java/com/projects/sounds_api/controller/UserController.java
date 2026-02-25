package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.admin.dto.UserDetails;
import com.projects.sounds_api.domain.user.dto.UserEditAccount;
import com.projects.sounds_api.domain.user.services.UserControllerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserControllerService userControllerService;

    @PutMapping("/account/edit")
    public ResponseEntity<UserDetails> editAccount(@RequestBody @Valid UserEditAccount data) {
        var user = userControllerService.editAccount(data);
        return ResponseEntity.ok(new UserDetails(user));
    }

    @DeleteMapping("/account/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        userControllerService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

}
