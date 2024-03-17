package com.ts.org.mobiledevicemangment.controller;

import com.ts.org.mobiledevicemangment.cqrs.command.booking.BookingDeviceCommandHandler;
import com.ts.org.mobiledevicemangment.cqrs.command.booking.CreateBookingCommand;
import com.ts.org.mobiledevicemangment.cqrs.command.booking.UpdateBookingStatusCommand;
import com.ts.org.mobiledevicemangment.cqrs.queries.booking.BookingDeviceQueryService;
import com.ts.org.mobiledevicemangment.cqrs.queries.booking.BookingDeviceRecordDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingDeviceQueryService bookingDeviceQueryService;
    private final BookingDeviceCommandHandler bookingDeviceCommandHandler;

    public BookingController(BookingDeviceQueryService bookingDeviceQueryService, BookingDeviceCommandHandler bookingDeviceCommandHandler) {
        this.bookingDeviceQueryService = bookingDeviceQueryService;
        this.bookingDeviceCommandHandler = bookingDeviceCommandHandler;
    }

    @PostMapping
    public ResponseEntity<BookingDeviceRecordDto> createBooking(@Valid @RequestBody CreateBookingCommand createBookingCommand) {
        Optional<BookingDeviceRecordDto> newBooking = bookingDeviceCommandHandler.bookDevice(createBookingCommand);
        return newBooking.map(booking -> new ResponseEntity<>(booking, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Void> updateBookingStatus(@PathVariable Long bookingId,
                                                    @Valid @RequestBody UpdateBookingStatusCommand updateBookingStatusCommand) {
        boolean success = bookingDeviceCommandHandler.updateBookingStatus(bookingId, updateBookingStatusCommand.bookingStatus());
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BookingDeviceRecordDto>> getAllBookings() {
        return ResponseEntity.ok(bookingDeviceQueryService.findAllBookings());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDeviceRecordDto> getBookingById(@PathVariable Long bookingId) {
        return bookingDeviceQueryService.
                findBookingById(bookingId).
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }


}
