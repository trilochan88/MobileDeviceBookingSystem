package com.ts.org.mobiledevicemangment.cqrs.command.booking;

import com.ts.org.mobiledevicemangment.common.enums.BookingStatus;
import com.ts.org.mobiledevicemangment.model.internal.db.BookingDeviceRecord;

import java.time.LocalDateTime;

public record CreateBookingCommand(Long userId, Long deviceId, int bookingDurationDays) {
    public BookingDeviceRecord createBookingRecord() {
        BookingDeviceRecord bookingDeviceRecord = new BookingDeviceRecord();
        bookingDeviceRecord.setDeviceId(deviceId());
        bookingDeviceRecord.setDeviceUserId(userId());
        bookingDeviceRecord.setBookingStartTime(LocalDateTime.now());
        bookingDeviceRecord.setBookingEndTime(LocalDateTime.now().plusDays(bookingDurationDays()));
        bookingDeviceRecord.setStatus(BookingStatus.Active);
        return bookingDeviceRecord;
    }
}
