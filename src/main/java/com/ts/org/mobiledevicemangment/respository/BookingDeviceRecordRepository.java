package com.ts.org.mobiledevicemangment.respository;

import com.ts.org.mobiledevicemangment.model.internal.db.BookingDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingDeviceRecordRepository extends JpaRepository<BookingDeviceRecord, Long> {
    @Query("SELECT b FROM BookingDeviceRecord b WHERE b.deviceId = :deviceId AND b.deviceUserId = :userId ORDER BY b.bookingStartTime DESC")
    Optional<BookingDeviceRecord> findMostRecentBookingByDeviceIdAndUserId(@Param("deviceId") Long deviceId, @Param("userId") Long userId);
}
