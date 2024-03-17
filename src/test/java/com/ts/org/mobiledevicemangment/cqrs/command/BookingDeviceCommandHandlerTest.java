package com.ts.org.mobiledevicemangment.cqrs.command;

import com.ts.org.mobiledevicemangment.common.enums.BookingStatus;
import com.ts.org.mobiledevicemangment.cqrs.command.booking.BookingDeviceCommandHandler;
import com.ts.org.mobiledevicemangment.cqrs.command.booking.CreateBookingCommand;
import com.ts.org.mobiledevicemangment.cqrs.queries.booking.BookingDeviceRecordDto;
import com.ts.org.mobiledevicemangment.model.internal.db.BookingDeviceRecord;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import com.ts.org.mobiledevicemangment.respository.BookingDeviceRecordRepository;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import com.ts.org.mobiledevicemangment.respository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class BookingDeviceCommandHandlerTest {
    @Mock
    private BookingDeviceRecordRepository bookingDeviceRecordRepository;
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BookingDeviceCommandHandler bookingDeviceCommandHandler;

    private CreateBookingCommand createBookingCommand;

    @Captor
    private ArgumentCaptor<Device> deviceCaptor;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldNotBookDeviceIfUserHasActiveBooking() {
        createBookingCommand = new CreateBookingCommand(1L,1L,2);
        Optional<BookingDeviceRecord> existingBooking = Optional.of(new BookingDeviceRecord());
        existingBooking.get().setStatus(BookingStatus.Active);
        when(userRepository.existsById(anyLong())).thenReturn(true);

        when(bookingDeviceRecordRepository.findMostRecentBookingByDeviceIdAndUserId(1L, 1L)).thenReturn(existingBooking);

        Optional<BookingDeviceRecordDto> result = bookingDeviceCommandHandler.bookDevice(createBookingCommand);

        // Assertions
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldBookDeviceIfNoActiveBookingExists() {
        //arrange
        createBookingCommand = new CreateBookingCommand(1L,1L,2);
        var bookingDeviceRecord = new BookingDeviceRecord();
        bookingDeviceRecord.setBookingId(200L);
        bookingDeviceRecord.setDeviceUserId(1L);
        bookingDeviceRecord.setBookingStartTime(LocalDateTime.now());
        bookingDeviceRecord.setBookingEndTime(LocalDateTime.now().plusDays(2));
        bookingDeviceRecord.setBookingId(1L);
        bookingDeviceRecord.setDeviceId(1L);
        bookingDeviceRecord.setStatus(BookingStatus.Active);

        when(bookingDeviceRecordRepository.findMostRecentBookingByDeviceIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingDeviceRecordRepository.save(any(BookingDeviceRecord.class))).thenReturn(bookingDeviceRecord);
        Device device = new Device();
        device.setIsDeviceAvailable(true);
        device.setDeviceId(1L);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        Optional<BookingDeviceRecordDto> result = bookingDeviceCommandHandler.bookDevice(createBookingCommand);

        assertTrue(result.isPresent());
    }
    @Test
    void shouldUpdateBookingStatusToReturnedAndMakeDeviceAvailable() {
        // Given an existing booking
        var bookingDeviceRecord = new BookingDeviceRecord();
        bookingDeviceRecord.setDeviceUserId(1L);
        bookingDeviceRecord.setBookingStartTime(LocalDateTime.now());
        bookingDeviceRecord.setBookingEndTime(LocalDateTime.now().plusDays(2));
        bookingDeviceRecord.setBookingId(1L);
        bookingDeviceRecord.setDeviceId(1L);
        bookingDeviceRecord.setStatus(BookingStatus.Active);

        when(bookingDeviceRecordRepository.findById(1L)).thenReturn(Optional.of(bookingDeviceRecord));
        when(deviceRepository.findById(anyLong())).thenReturn(Optional.of(new Device())); // Assume the device exists

        boolean success = bookingDeviceCommandHandler.updateBookingStatus(1L, BookingStatus.Returned);

        assertTrue(success);
        verify(deviceRepository, times(1)).save(deviceCaptor.capture());
        assertTrue(deviceCaptor.getValue().getIsDeviceAvailable()); // Assuming the device is now available
    }

}
