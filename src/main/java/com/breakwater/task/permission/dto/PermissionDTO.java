package com.breakwater.task.permission.dto;

import com.breakwater.task.permission.enums.PermissionCode;
import lombok.Value;

import java.util.UUID;

@Value
public class PermissionDTO {

    UUID id;

    PermissionCode permissionCode;
    UUID   departmentId;
    UUID   userId;

}
