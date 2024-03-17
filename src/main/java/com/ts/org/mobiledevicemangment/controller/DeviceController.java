package com.ts.org.mobiledevicemangment.controller;

import com.ts.org.mobiledevicemangment.cqrs.command.device.AddDeviceCommand;
import com.ts.org.mobiledevicemangment.cqrs.command.device.DeviceCommandHandler;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceQueryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceQueryService deviceQueryService;
    private final DeviceCommandHandler deviceCommandHandler;

    public DeviceController(DeviceQueryService deviceQueryService, DeviceCommandHandler deviceCommandHandler) {
        this.deviceQueryService = deviceQueryService;
        this.deviceCommandHandler = deviceCommandHandler;
    }

    @PostMapping
    public ResponseEntity<DeviceDto> addDevice(@Valid @RequestBody AddDeviceCommand addDeviceCommand) {
        DeviceDto savedDevice = deviceCommandHandler.addDevice(addDeviceCommand);
        return new ResponseEntity<>(savedDevice, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        List<DeviceDto> devices = deviceQueryService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long deviceId) {
        return deviceQueryService.getDeviceBy(deviceId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
