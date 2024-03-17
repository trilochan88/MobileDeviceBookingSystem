package com.ts.org.mobiledevicemangment.cqrs.queries.device;

import com.ts.org.mobiledevicemangment.common.enums.Platform;

public record DeviceDto(Long deviceId, String modelName, Platform platform, float osVersion, String brand, String technology,
                        String bands2G, String bands3G, String bands4G, Boolean isDeviceAvailable) {
}
