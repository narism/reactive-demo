package com.breakwater.task.permission.model;

import com.breakwater.task.permission.enums.PermissionCode;
import com.breakwater.task.permission.enums.PermissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@AllArgsConstructor
public class Permission {

    @Id
    UUID id;

    PermissionCode permissionCode;

    PermissionStatus status;
    UUID departmentId;
    UUID userId;

}
