package com.ts.org.mobiledevicemangment.cqrs.queries.device;

import com.ts.org.mobiledevicemangment.common.enums.Platform;
import com.ts.org.mobiledevicemangment.common.mapping.DbModelToDtoMapper;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {
    private final DeviceRepository deviceRepository;

    public DeviceQueryServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Optional<DeviceDto> getDeviceBy(Long deviceId) {
        Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
        return optionalDevice.map(DbModelToDtoMapper::toDto);
    }

    @Override
    public List<DeviceDto> getDevicesByPlatform(Platform platform) {
        return deviceRepository.findAllByPlatform(platform).stream().map(DbModelToDtoMapper::toDto).toList();
    }

    @Override
    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll().stream().map(DbModelToDtoMapper::toDto).toList();
    }
}
