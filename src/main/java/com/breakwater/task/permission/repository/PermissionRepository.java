package com.breakwater.task.permission.repository;

import com.breakwater.task.permission.enums.PermissionCode;
import com.breakwater.task.permission.model.Permission;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PermissionRepository extends ReactiveMongoRepository<Permission, UUID> {

    Flux<Permission> findByUserIdAndDepartmentId(UUID userId, UUID departmentId);

    Flux<Permission> findByUserId(UUID userId);

    Mono<Permission> findByUserIdAndId(UUID userId, UUID permissionId);

    @Query("select * from permission where userId = :userId")
    Mono<Permission> findActiveByUserIdAndDepartmentIdAndCode(@Param("userId") UUID userId, UUID departmentId, PermissionCode code);
}
