package com.ts.org.mobiledevicemangment.cqrs.queries.booking;

import com.ts.org.mobiledevicemangment.common.mapping.DbModelToDtoMapper;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;
import com.ts.org.mobiledevicemangment.respository.BookingDeviceRecordRepository;
import com.ts.org.mobiledevicemangment.respository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingDeviceQueryServiceImpl implements BookingDeviceQueryService {
    private final BookingDeviceRecordRepository bookingDeviceRecordRepository;
    private final DeviceRepository deviceRepository;

    public BookingDeviceQueryServiceImpl(
            BookingDeviceRecordRepository bookingDeviceRecordRepository,
            DeviceRepository deviceRepository) {
        this.bookingDeviceRecordRepository = bookingDeviceRecordRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<BookingDeviceRecordDto> findAllBookings() {
        return bookingDeviceRecordRepository.
                findAll().
                stream().
                map(DbModelToDtoMapper::toDto).
                collect(Collectors.toList());
    }


    @Override
    public Optional<BookingDeviceRecordDto> findBookingById(Long bookingId) {
        return bookingDeviceRecordRepository.
                findById(bookingId).
                map(DbModelToDtoMapper::toDto);
    }

    @Override
    public List<DeviceDto> findAllAvailableDevice() {
        return deviceRepository.
                findAllByIsDeviceAvailableTrue().
                stream().
                map(DbModelToDtoMapper::toDto).
                collect(Collectors.toList());
    }


}
