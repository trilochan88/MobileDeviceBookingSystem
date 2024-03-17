package com.ts.org.mobiledevicemangment.model.internal.db;

import com.ts.org.mobiledevicemangment.common.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
public class BookingDeviceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @NotNull
    private Long deviceId;
    @NotNull
    private Long deviceUserId;
    @NotNull(message = "booking start time is required")
    private LocalDateTime bookingStartTime;
    @NotNull(message = "booking end time is required")
    private LocalDateTime bookingEndTime;
    @NotNull(message = "Status is required")
    private BookingStatus status;
    @Version
    private Long version;
}
