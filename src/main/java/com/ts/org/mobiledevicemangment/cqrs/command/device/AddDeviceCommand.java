package com.ts.org.mobiledevicemangment.cqrs.command.device;

import com.ts.org.mobiledevicemangment.common.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddDeviceCommand(
        @NotBlank(message = "Model name must not be empty")
        String modelName,
        @NotNull(message = "platform must not be empty")
        Platform platform,
        @NotBlank(message = "Brand must not be empty")
        String brand,
        @NotNull(message = "os version must not be empty")
        float osVersion){}
