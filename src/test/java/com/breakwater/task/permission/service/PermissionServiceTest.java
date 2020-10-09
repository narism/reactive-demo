package com.breakwater.task.permission.service;

import com.breakwater.task.exception.BusinessException;
import com.breakwater.task.exception.NotFoundException;
import com.breakwater.task.permission.dto.PermissionCreateDTO;
import com.breakwater.task.permission.dto.PermissionDTO;
import com.breakwater.task.permission.enums.PermissionCode;
import com.breakwater.task.permission.enums.PermissionStatus;
import com.breakwater.task.permission.model.Permission;
import com.breakwater.task.permission.repository.PermissionRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;
import java.util.UUID;

public class PermissionServiceTest {

    private static final UUID PERMISSION_ID = UUID.randomUUID();
    private static final UUID DEPARTMENT_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();

    @Tested
    private PermissionService subject;

    @Injectable
    private PermissionRepository permissionRepository;

    @Test
    public void correctPermissionsFoundWhenNoDepartmentProvided() {
        var expectedPermission = new PermissionDTO(PERMISSION_ID, PermissionCode.VIEW, DEPARTMENT_ID, USER_ID);
        new Expectations() {{
            permissionRepository.findByUserIdAndStatus(USER_ID, PermissionStatus.ACTIVE);
            result = Flux.just(new Permission(PERMISSION_ID, PermissionCode.VIEW, PermissionStatus.ACTIVE,
                    DEPARTMENT_ID, USER_ID));
        }};

        StepVerifier.create(subject.getPermissionsByUserIdAndDepartmentId(USER_ID, Optional.empty()))
                .expectNext(expectedPermission)
                .verifyComplete();
    }

    @Test
    public void correctPermissionsFoundWhenDepartmentProvided() {
        var expectedPermission = new PermissionDTO(PERMISSION_ID, PermissionCode.VIEW, DEPARTMENT_ID, USER_ID);
        new Expectations() {{
            permissionRepository.findByUserIdAndDepartmentIdAndStatus(USER_ID, DEPARTMENT_ID, PermissionStatus.ACTIVE);
            result = Flux.just(new Permission(PERMISSION_ID, PermissionCode.VIEW, PermissionStatus.ACTIVE,
                    DEPARTMENT_ID, USER_ID));
        }};

        StepVerifier.create(subject.getPermissionsByUserIdAndDepartmentId(USER_ID, Optional.of(DEPARTMENT_ID)))
                .expectNext(expectedPermission)
                .verifyComplete();
    }

    @Test
    public void noElementsReturnedWhenNoPermissions() {
        new Expectations() {{
            permissionRepository.findByUserIdAndDepartmentIdAndStatus(USER_ID, DEPARTMENT_ID, PermissionStatus.ACTIVE);
            result = Flux.empty();
        }};

        StepVerifier.create(subject.getPermissionsByUserIdAndDepartmentId(USER_ID, Optional.of(DEPARTMENT_ID)))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void invalidPermissionIdThrowsException() {
        new Expectations() {{
            permissionRepository.findByUserIdAndIdAndStatus(USER_ID, PERMISSION_ID, PermissionStatus.ACTIVE);
            result = Mono.empty();
        }};

        StepVerifier.create(subject.getUsersPermissionByPermissionId(USER_ID, PERMISSION_ID))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    public void correctPermissionIdReturnsPermission() {
        var expectedPermission = new PermissionDTO(PERMISSION_ID, PermissionCode.VIEW, DEPARTMENT_ID, USER_ID);
        new Expectations() {{
            permissionRepository.findByUserIdAndIdAndStatus(USER_ID, PERMISSION_ID, PermissionStatus.ACTIVE);
            result = Mono.just(new Permission(PERMISSION_ID, PermissionCode.VIEW, PermissionStatus.ACTIVE,
                    DEPARTMENT_ID, USER_ID));
        }};

        StepVerifier.create(subject.getUsersPermissionByPermissionId(USER_ID, PERMISSION_ID))
                .expectNext(expectedPermission)
                .verifyComplete();
    }

    @Test
    public void unableToCreateSecondActivePermissionForUserAndDepartment() {
        new Expectations() {{
            permissionRepository.findByUserIdAndDepartmentIdAndPermissionCodeAndStatus(USER_ID, DEPARTMENT_ID,
                    PermissionCode.VIEW, PermissionStatus.ACTIVE);
            result = Mono.just(new Permission(PERMISSION_ID, PermissionCode.VIEW, PermissionStatus.ACTIVE,
                    DEPARTMENT_ID, USER_ID));
        }};

        StepVerifier.create(subject.createUsersPermission(USER_ID, new PermissionCreateDTO(PermissionCode.VIEW,
                DEPARTMENT_ID)))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    public void createNewActivePermissionForUserAndDepartment() {
        var expectedPermission = new PermissionDTO(PERMISSION_ID, PermissionCode.VIEW, DEPARTMENT_ID, USER_ID);

        var expectedPermissionEntity = new Permission(PERMISSION_ID, PermissionCode.VIEW, PermissionStatus.ACTIVE,
                DEPARTMENT_ID, USER_ID);

        new Expectations() {{
            permissionRepository.findByUserIdAndDepartmentIdAndPermissionCodeAndStatus(USER_ID, DEPARTMENT_ID,
                    PermissionCode.VIEW, PermissionStatus.ACTIVE);
            result = Mono.empty();

            permissionRepository.save(withAny(expectedPermissionEntity));
            result = Mono.just(expectedPermissionEntity);
        }};

        StepVerifier.create(subject.createUsersPermission(USER_ID, new PermissionCreateDTO(PermissionCode.VIEW,
                DEPARTMENT_ID)))
                .expectNext(expectedPermission)
                .verifyComplete();
    }

    @Test
    public void revokeInvalidPermissionThrowsNotFoundException() {
        new Expectations() {{
            permissionRepository.findByUserIdAndIdAndStatus(USER_ID, PERMISSION_ID, PermissionStatus.ACTIVE);
            result = Mono.empty();
        }};

        StepVerifier.create(subject.revokeUsersPermission(USER_ID, PERMISSION_ID))
                .expectError(NotFoundException.class)
                .verify();
    }
}
