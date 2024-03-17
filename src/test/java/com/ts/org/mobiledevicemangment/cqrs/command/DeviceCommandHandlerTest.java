package com.ts.org.mobiledevicemangment.cqrs.command;

import com.ts.org.mobiledevicemangment.common.enums.Platform;
import com.ts.org.mobiledevicemangment.cqrs.command.device.AddDeviceCommand;
import com.ts.org.mobiledevicemangment.cqrs.command.device.DeviceCommandHandler;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;
import com.ts.org.mobiledevicemangment.external.FonoApiClient;
import com.ts.org.mobiledevicemangment.external.FonoApiResponse;
import com.ts.org.mobiledevicemangment.external.FonoDeviceRequest;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceCommandHandlerTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private FonoApiClient fonoApiClient;

    @InjectMocks
    private DeviceCommandHandler deviceCommandHandler;

    private AddDeviceCommand addDeviceCommand;
    private Device device;
    private DeviceDto deviceDto;

    @BeforeEach
    void setUp() {
        addDeviceCommand = mock(AddDeviceCommand.class);
        when(addDeviceCommand.modelName()).thenReturn("Test Model");
        when(addDeviceCommand.platform()).thenReturn(Platform.IOS);
        when(addDeviceCommand.osVersion()).thenReturn(1f);

        device = new Device();
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
    void addDeviceShouldReturnDeviceDto() {
        // Arrange
        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        when(fonoApiClient.getDevice(any(FonoDeviceRequest.class))).thenReturn(Optional.of(new FonoApiResponse("Apple iphone 14pro", "test", "test", "test", "test")));

        // Act
        DeviceDto result = deviceCommandHandler.addDevice(addDeviceCommand);

        // Assert
        assertNotNull(result);
        assertEquals(deviceDto, result);

        // Verify interactions
        verify(deviceRepository).save(any(Device.class));

    }
}

