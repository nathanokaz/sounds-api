package com.projects.sounds_api.domain.admin.dto;

import com.projects.sounds_api.domain.user.dto.UserRoles;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AlterRoleData(

        @NotNull
        @Min(1)
        Long id,
        @NotNull
        UserRoles role) {
}
