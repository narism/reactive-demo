package com.breakwater.task.permission.dto;

import com.breakwater.task.permission.enums.PermissionCode;
import lombok.Value;

import java.util.UUID;

@Value
public class PermissionCreateDTO {

    PermissionCode permissionCode;
    UUID   departmentId;

}
