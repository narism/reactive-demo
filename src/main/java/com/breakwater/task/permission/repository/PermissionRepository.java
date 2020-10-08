package com.breakwater.task.permission.repository;

import com.breakwater.task.permission.enums.PermissionCode;
import com.breakwater.task.permission.enums.PermissionStatus;
import com.breakwater.task.permission.model.Permission;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PermissionRepository extends ReactiveMongoRepository<Permission, UUID> {

    Flux<Permission> findByUserIdAndDepartmentIdAndStatus(UUID userId, UUID departmentId, PermissionStatus status);

    Mono<Permission> findByUserIdAndIdAndStatus(UUID userId, UUID permissionId, PermissionStatus status);

    Mono<Permission> findByUserIdAndDepartmentIdAndPermissionCodeAndStatus(UUID userId, UUID departmentId, PermissionCode permissionCode, PermissionStatus status);

    Flux<Permission> findByUserIdAndStatus(UUID userId, PermissionStatus status);
}
