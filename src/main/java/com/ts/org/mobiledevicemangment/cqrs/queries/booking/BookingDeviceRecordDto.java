package com.ts.org.mobiledevicemangment.cqrs.queries.booking;

import com.ts.org.mobiledevicemangment.common.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingDeviceRecordDto(Long bookingId, Long deviceId, Long userId, LocalDateTime bookingStartTime,
                                     LocalDateTime bookingEndTime, BookingStatus status) {
}
