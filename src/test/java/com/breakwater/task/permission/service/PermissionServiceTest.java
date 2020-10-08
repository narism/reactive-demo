package com.breakwater.task.permission.service;

import com.breakwater.task.department.dto.DepartmentDTO;
import com.breakwater.task.department.model.Department;
import com.breakwater.task.department.repository.DepartmentRepository;
import com.breakwater.task.department.service.DepartmentService;
import com.breakwater.task.permission.repository.PermissionRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

public class PermissionServiceTest {

    @Tested
    private PermissionService subject;

    @Injectable
    private PermissionRepository permissionRepository;

//    @Test
//    public void permissionsFound() {
//        var expectedParentDepartment = new DepartmentDTO(PARENT_ID, PARENT_ID.toString(), null, List.of());
//        var expectedChildDepartment = new DepartmentDTO(CHILD_ID, CHILD_ID.toString(), expectedParentDepartment, List.of());
//        new Expectations() {{
//            departmentRepository.findById(CHILD_ID);
//            result = Mono.just(new Department(CHILD_ID, CHILD_ID.toString(), PARENT_ID));
//
//            departmentRepository.findById(PARENT_ID);
//            result = Mono.just(new Department(PARENT_ID, PARENT_ID.toString(), null));
//        }};
//
//        StepVerifier.create(subject.readDepartmentWithParents(CHILD_ID))
//                .expectNext(expectedChildDepartment)
//                .verifyComplete();
//    }
}
