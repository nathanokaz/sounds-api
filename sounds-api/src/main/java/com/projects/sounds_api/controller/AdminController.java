package com.projects.sounds_api.controller;

import com.projects.sounds_api.domain.admin.dto.AlterRoleData;
import com.projects.sounds_api.domain.admin.dto.UserDetails;
import com.projects.sounds_api.domain.admin.services.AdminControllerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "bearer-key")
public class AdminController {

    @Autowired
    private AdminControllerService adminControllerService;

    @GetMapping("/show/users")
    public ResponseEntity<Page<UserDetails>> showUsers(@PageableDefault(size = 5, sort = {"username"}) Pageable pageable) {
        var page = adminControllerService.showUsers(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/show/user/{id}")
    public ResponseEntity<UserDetails> showUserByid(@PathVariable Long id) {
        var user = adminControllerService.showUserById(id);
        return ResponseEntity.ok(new UserDetails(user));
    }

    @PutMapping("/alter/role")
    public ResponseEntity<UserDetails> alterUserRole(@RequestBody @Valid AlterRoleData data) {
        var user = adminControllerService.alterUserRole(data);
        return ResponseEntity.ok(new UserDetails(user));
    }

    @DeleteMapping("/account/ban/{id}")
    public ResponseEntity<?> userAccountBan(@PathVariable Long id) {
        adminControllerService.userAccountBan(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/account/reactivate/{id}")
    public ResponseEntity<?> userAccountReactivate(@PathVariable Long id) {
        adminControllerService.userAccountReactivate(id);
        return ResponseEntity.ok().build();
    }

}
