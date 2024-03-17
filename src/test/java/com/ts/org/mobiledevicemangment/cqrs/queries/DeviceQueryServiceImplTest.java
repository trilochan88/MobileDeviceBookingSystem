package com.ts.org.mobiledevicemangment.cqrs.queries;

import com.ts.org.mobiledevicemangment.common.enums.Platform;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceQueryServiceImpl;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceQueryServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceQueryServiceImpl deviceQueryService;

    // Setup devices for testing
    private Device device;
    private DeviceDto deviceDto; // Assume this is a valid DTO class you have

    @BeforeEach
    void setUp() {
        device = new Device(); // Setup device object
        device.setDeviceId(1L);
        device.setOsVersion(1L);
        device.setIsDeviceAvailable(true);
        device.setPlatform(Platform.IOS);
        device.setModelName("Apple iphone 14pro");
        device.setBrand("Apple");
        device.setTechnology("test");
        device.setBands2G("test");
        device.setBands3G("test");
        device.setBands4G("test");
        deviceDto = new DeviceDto(1L,
                "Apple iphone 14pro",
                Platform.IOS, 1L, "Apple", "test",
                "test", "test",
                "test", true);

    }

    @Test
    void whenGetDeviceById_thenSuccess() {
        when(deviceRepository.findById(anyLong())).thenReturn(Optional.of(device));

        Optional<DeviceDto> foundDeviceDto = deviceQueryService.getDeviceBy(1L);

        assertTrue(foundDeviceDto.isPresent());
        assertEquals(deviceDto, foundDeviceDto.get());
    }

    @Test
    void whenGetAllDevices_thenSuccess() {
        List<Device> devices = List.of(device);
        when(deviceRepository.findAll()).thenReturn(devices);

        List<DeviceDto> deviceDtos = deviceQueryService.getAllDevices();

        assertFalse(deviceDtos.isEmpty());
        assertEquals(1, deviceDtos.size());
        assertEquals(deviceDto, deviceDtos.get(0));
    }


}

