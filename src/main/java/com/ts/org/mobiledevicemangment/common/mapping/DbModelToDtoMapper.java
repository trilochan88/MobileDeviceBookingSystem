package com.ts.org.mobiledevicemangment.common.mapping;

import com.ts.org.mobiledevicemangment.cqrs.queries.booking.BookingDeviceRecordDto;
import com.ts.org.mobiledevicemangment.cqrs.queries.device.DeviceDto;
import com.ts.org.mobiledevicemangment.cqrs.queries.user.DeviceUserDto;
import com.ts.org.mobiledevicemangment.model.internal.db.BookingDeviceRecord;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import com.ts.org.mobiledevicemangment.model.internal.db.DeviceUser;

public final class DbModelToDtoMapper {

    private DbModelToDtoMapper(){}
    public static BookingDeviceRecordDto toDto(BookingDeviceRecord bookingDeviceRecord) {
        return new BookingDeviceRecordDto(
                bookingDeviceRecord.getBookingId(),
                bookingDeviceRecord.getDeviceId(),
                bookingDeviceRecord.getDeviceUserId(),
                bookingDeviceRecord.getBookingStartTime(),
                bookingDeviceRecord.getBookingEndTime(),
                bookingDeviceRecord.getStatus()
        );
    }

    public static DeviceDto toDto(Device device) {
        return new DeviceDto(
                device.getDeviceId(),
                device.getModelName(),
                device.getPlatform(),
                device.getOsVersion(),
                device.getBrand(),
                device.getTechnology(),
                device.getBands2G(),
                device.getBands3G(),
                device.getBands4G(),
                device.getIsDeviceAvailable());
    }

    public static DeviceUserDto toDto(DeviceUser deviceUser) {
        return new DeviceUserDto(deviceUser.getDeviceUserId(), deviceUser.getName(), deviceUser.getEmail(), deviceUser.getEmployeeId(), deviceUser.getRole());
    }
}
