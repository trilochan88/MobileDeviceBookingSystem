package com.ts.org.mobiledevicemangment.cqrs.command.device;

import com.ts.org.mobiledevicemangment.common.mapping.DbModelToDtoMapper;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;
import com.ts.org.mobiledevicemangment.external.FonoApiClient;
import com.ts.org.mobiledevicemangment.external.FonoApiResponse;
import com.ts.org.mobiledevicemangment.external.FonoDeviceRequest;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceCommandHandler {
    private final DeviceRepository deviceRepository;
    private final FonoApiClient fonoApiClient;


    public DeviceCommandHandler(DeviceRepository deviceRepository, @Qualifier("fonoApiClientFallback") FonoApiClient fonoApiClient) {
        this.deviceRepository = deviceRepository;
        this.fonoApiClient = fonoApiClient;
    }

    public DeviceDto addDevice(AddDeviceCommand addDeviceCommand) {
        Device device = new Device();
        device.setModelName(addDeviceCommand.modelName());
        device.setPlatform(addDeviceCommand.platform());
        device.setIsDeviceAvailable(true);
        device.setBrand(addDeviceCommand.brand());
        device.setOsVersion(addDeviceCommand.osVersion());
        var optionalDeviceData = getDeviceBandsInfo(addDeviceCommand.modelName());
        if (optionalDeviceData.isPresent()) {
            var deviceData = optionalDeviceData.get();
            device.setTechnology(deviceData.getTechnology());
            device.setBands2G(deviceData.getBands2g());
            device.setBands3G(deviceData.getBands3g());
            device.setBands4G(deviceData.getBands4g());
        }
        var savedDevice = deviceRepository.save(device);
        return DbModelToDtoMapper.toDto(savedDevice);
    }

    public Optional<FonoApiResponse> getDeviceBandsInfo(String modelName) {
        var fonoApiRequest = new FonoDeviceRequest();
        fonoApiRequest.setLimit(1);
        fonoApiRequest.setToken("12323");
        fonoApiRequest.setModelName(modelName);
        return fonoApiClient.getDevice(fonoApiRequest);
    }
}
