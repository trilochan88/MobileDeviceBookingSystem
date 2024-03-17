package com.ts.org.mobiledevicemangment.cqrs.command.booking;

import com.ts.org.mobiledevicemangment.common.enums.BookingStatus;
import com.ts.org.mobiledevicemangment.common.mapping.DbModelToDtoMapper;
import com.ts.org.mobiledevicemangment.cqrs.queries.booking.BookingDeviceRecordDto;
import com.ts.org.mobiledevicemangment.model.internal.db.BookingDeviceRecord;
import com.ts.org.mobiledevicemangment.respository.BookingDeviceRecordRepository;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import com.ts.org.mobiledevicemangment.respository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingDeviceCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(BookingDeviceCommandHandler.class);

    private final BookingDeviceRecordRepository bookingDeviceRecordRepository;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public BookingDeviceCommandHandler(BookingDeviceRecordRepository bookingDeviceRecordRepository, DeviceRepository deviceRepository, UserRepository userRepository) {
        this.bookingDeviceRecordRepository = bookingDeviceRecordRepository;
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<BookingDeviceRecordDto> bookDevice(CreateBookingCommand createBookingCommand) {
        try {
            if (!userRepository.existsById(createBookingCommand.userId())) {
                logger.error("User is not found %d".formatted(createBookingCommand.userId()));
                return Optional.empty();//can be replaced with exceptions
            }
            Optional<BookingDeviceRecord> mostRecentBooking = bookingDeviceRecordRepository.findMostRecentBookingByDeviceIdAndUserId(createBookingCommand.deviceId(), createBookingCommand.userId());
            if (mostRecentBooking.isPresent()
                    &&
                    !mostRecentBooking.get().getStatus().
                            equals(BookingStatus.Returned)) {
                return Optional.empty();
            }

            return deviceRepository.findById(createBookingCommand.deviceId()).flatMap(device -> {
                if (device.getIsDeviceAvailable().equals(Boolean.TRUE)) {
                    device.setIsDeviceAvailable(false);
                    device.setLastBookedBy(createBookingCommand.userId());
                    device.setLastBookedOn(LocalDateTime.now());
                    deviceRepository.save(device);
                    BookingDeviceRecord bookingDeviceRecord = createBookingCommand.createBookingRecord();
                    var savedBooking = bookingDeviceRecordRepository.save(bookingDeviceRecord);
                    return Optional.of(DbModelToDtoMapper.toDto(savedBooking));
                } else {
                    return Optional.empty();
                }
            });
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("ObjectOptimisticLockingFailureException Concurrency issue", e);
            return Optional.empty();
        } catch (Exception ex) {
            logger.error("Error while saving booking data", ex);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean updateBookingStatus(Long bookingId, BookingStatus newStatus) {
        try {
            var bookingDeviceRecord = bookingDeviceRecordRepository.findById(bookingId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid booking id"));
            bookingDeviceRecord.setStatus(newStatus);
            bookingDeviceRecordRepository.save(bookingDeviceRecord);
            if (BookingStatus.Returned == newStatus) {
                var optionalDevice = deviceRepository.findById(bookingDeviceRecord.getDeviceId());
                if (optionalDevice.isPresent()) {
                    var device = optionalDevice.get();
                    device.setIsDeviceAvailable(true);
                    device.setLastBookedOn(LocalDateTime.now());
                    deviceRepository.save(device);
                }
            }
            return true;
        } catch (ObjectOptimisticLockingFailureException e) {
            return false;
        }
    }
}
