package com.ts.org.mobiledevicemangment.cqrs.queries.user;

import java.util.List;
import java.util.Optional;

public interface DeviceUserQueryService {
    List<DeviceUserDto> findAllUsers();
    Optional<DeviceUserDto> findUserById(Long userId);
}
