package com.ts.org.mobiledevicemangment.cqrs.command.booking;

import com.ts.org.mobiledevicemangment.common.enums.BookingStatus;

public record UpdateBookingStatusCommand(BookingStatus bookingStatus) {
}
