package com.ts.org.mobiledevicemangment.cqrs.queries.device;

import com.ts.org.mobiledevicemangment.common.enums.Platform;

import java.util.List;
import java.util.Optional;

public interface DeviceQueryService {
    Optional<DeviceDto> getDeviceBy(Long deviceId);
    List<DeviceDto> getDevicesByPlatform(Platform platform);

    List<DeviceDto> getAllDevices();
}
