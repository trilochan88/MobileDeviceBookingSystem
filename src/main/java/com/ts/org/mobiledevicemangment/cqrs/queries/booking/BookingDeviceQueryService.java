package com.ts.org.mobiledevicemangment.cqrs.queries.booking;

import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;

import java.util.List;
import java.util.Optional;

public interface BookingDeviceQueryService {
    List<BookingDeviceRecordDto> findAllBookings();
    Optional<BookingDeviceRecordDto> findBookingById(Long bookingId);
    List<DeviceDto> findAllAvailableDevice();
}
