package com.ts.org.mobiledevicemangment.cqrs.queries;

import com.ts.org.mobiledevicemangment.common.enums.BookingStatus;
import com.ts.org.mobiledevicemangment.common.enums.Platform;
import com.ts.org.mobiledevicemangment.cqrs.queries.booking.BookingDeviceQueryServiceImpl;
import com.ts.org.mobiledevicemangment.cqrs.queries.booking.BookingDeviceRecordDto;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;
import com.ts.org.mobiledevicemangment.model.internal.db.BookingDeviceRecord;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import com.ts.org.mobiledevicemangment.respository.BookingDeviceRecordRepository;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingDeviceQueryServiceImplTest {

    @Mock
    private BookingDeviceRecordRepository bookingDeviceRecordRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private BookingDeviceQueryServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindAllBookings() {
        // Setup
        var record = new BookingDeviceRecord();
        record.setDeviceUserId(1L);
        record.setBookingStartTime(LocalDateTime.now());
        record.setBookingEndTime(LocalDateTime.now().plusDays(2));
        record.setBookingId(1L);
        record.setDeviceId(1L);
        record.setStatus(BookingStatus.Active);
        when(bookingDeviceRecordRepository.findAll()).thenReturn(List.of(record));

        // Execute
        List<BookingDeviceRecordDto> result = service.findAllBookings();

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindBookingById() {
        // Setup
        var record = new BookingDeviceRecord();
        record.setDeviceUserId(1L);
        record.setBookingStartTime(LocalDateTime.now());
        record.setBookingEndTime(LocalDateTime.now().plusDays(2));
        record.setBookingId(1L);
        record.setDeviceId(1L);
        record.setStatus(BookingStatus.Active);
        Optional<BookingDeviceRecord> optionalBookingDeviceRecord = Optional.of(record);
        when(bookingDeviceRecordRepository.findById(anyLong())).thenReturn(optionalBookingDeviceRecord);

        // Execute
        Optional<BookingDeviceRecordDto> result = service.findBookingById(1L);

        // Verify
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAllAvailableDevice() {
        // Setup
        Device device = new Device();
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
        when(deviceRepository.findAllByIsDeviceAvailableTrue()).thenReturn(List.of(device));

        // Execute
        List<DeviceDto> result = service.findAllAvailableDevice();

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
    }



}

