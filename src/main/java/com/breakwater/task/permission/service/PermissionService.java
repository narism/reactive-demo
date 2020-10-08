package com.breakwater.task.permission.service;

import com.breakwater.task.exception.BusinessException;
import com.breakwater.task.exception.NotFoundException;
import com.breakwater.task.permission.dto.PermissionCreateDTO;
import com.breakwater.task.permission.dto.PermissionDTO;
import com.breakwater.task.permission.model.Permission;
import com.breakwater.task.permission.repository.PermissionRepository;
import com.breakwater.task.security.AllowPermissionEdit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static com.breakwater.task.permission.enums.PermissionStatus.ACTIVE;
import static com.breakwater.task.permission.enums.PermissionStatus.REVOKED;
import static java.util.UUID.randomUUID;
import static reactor.core.publisher.Mono.*;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @AllowPermissionEdit
    public Flux<PermissionDTO> getPermissionsByUserIdAndDepartmentId(UUID userId, Optional<UUID> opDepartmentId) {
        return opDepartmentId.map(departmentId -> permissionRepository.findByUserIdAndDepartmentIdAndStatus(userId, departmentId, ACTIVE))
                .orElseGet(() -> permissionRepository.findByUserIdAndStatus(userId, ACTIVE))
                .flatMap(this::mapToDTO);
    }

    @AllowPermissionEdit
    public Mono<PermissionDTO> getUsersPermissionByPermissionId(UUID userId, UUID permissionId) {
        return permissionRepository.findByUserIdAndIdAndStatus(userId, permissionId, ACTIVE)
                .flatMap(this::mapToDTO)
                .switchIfEmpty(defer(() -> error(new NotFoundException("Permission not found"))));
    }

    @Transactional
    @AllowPermissionEdit
    public Mono<PermissionDTO> revokeUsersPermission(UUID userId, UUID permissionId) {
        return permissionRepository.findByUserIdAndIdAndStatus(userId, permissionId, ACTIVE)
                .flatMap(this::revokePermission)
                .flatMap(permissionRepository::save)
                .switchIfEmpty(defer(() -> error(new NotFoundException("Permission not found"))))
                .flatMap(this::mapToDTO);
    }

    @Transactional
    @AllowPermissionEdit
    public Mono<PermissionDTO> createUsersPermission(UUID userId, PermissionCreateDTO permission) {
        return permissionRepository.findByUserIdAndDepartmentIdAndPermissionCodeAndStatus
                (userId, permission.getDepartmentId(), permission.getPermissionCode(), ACTIVE)
                .flatMap(existingPermission -> error(new BusinessException("Permission already exists")))
                .switchIfEmpty(defer(() -> permissionRepository.save(createNewPermission(permission, userId))))
                .cast(Permission.class)
                .flatMap(this::mapToDTO);
    }

    private Mono<Permission> revokePermission(Permission permission) {
        permission.setStatus(REVOKED);
        return just(permission);
    }

    private Mono<PermissionDTO> mapToDTO(Permission permission) {
        return just(new PermissionDTO(permission.getId(),
                permission.getPermissionCode(),
                permission.getDepartmentId(),
                permission.getUserId()));
    }

    private Permission createNewPermission(PermissionCreateDTO permissionDTO, UUID userId) {
        return new Permission(
                randomUUID(),
                permissionDTO.getPermissionCode(),
                ACTIVE,
                permissionDTO.getDepartmentId(),
                userId);
    }
}
