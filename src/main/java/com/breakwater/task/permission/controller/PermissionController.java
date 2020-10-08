package com.breakwater.task.permission.controller;

import com.breakwater.task.permission.dto.PermissionCreateDTO;
import com.breakwater.task.permission.dto.PermissionDTO;
import com.breakwater.task.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/permissions")
    public Mono<PermissionDTO> createUsersPermission(@PathVariable UUID userId,
                                                     @RequestBody PermissionCreateDTO permission) {
        return permissionService.createUsersPermission(userId, permission);
    }

    @GetMapping("/users/{userId}/permissions")
    public Flux<PermissionDTO> getUsersPermissions(@PathVariable UUID userId,
                                                   @RequestParam(required = false) Optional<UUID> departmentId) {
        return permissionService.getPermissionsByUserIdAndDepartmentId(userId, departmentId);
    }

    @GetMapping("/users/{userId}/permissions/{permissionId}")
    public Mono<PermissionDTO> getUsersPermissionByPermissionId(@PathVariable UUID userId,
                                                                @PathVariable UUID permissionId) {
        return permissionService.getUsersPermissionByPermissionId(userId, permissionId);
    }


    @DeleteMapping("/users/{userId}/permissions/{permissionId}")
    public Mono<PermissionDTO> revokeUsersPermission(@PathVariable UUID userId, @PathVariable UUID permissionId) {
        return permissionService.revokeUsersPermission(userId, permissionId);
    }
}
