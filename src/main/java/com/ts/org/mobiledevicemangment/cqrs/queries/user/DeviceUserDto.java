package com.ts.org.mobiledevicemangment.cqrs.queries.user;

import com.ts.org.mobiledevicemangment.common.enums.Role;

public record DeviceUserDto(Long userId, String name, String email, String employeeId, Role role) {
}
