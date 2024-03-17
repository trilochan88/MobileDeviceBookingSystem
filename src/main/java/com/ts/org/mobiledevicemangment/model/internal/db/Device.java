package com.ts.org.mobiledevicemangment.model.internal.db;

import com.ts.org.mobiledevicemangment.common.enums.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Entity(name = "device")
@Table(indexes = @Index(name = "idx_device_modelName", columnList = "modelName"))
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    @NotBlank(message = "Model name is required")
    @Column(unique = true)
    private String modelName;
    private Platform platform;
    private float osVersion;
    private String brand;
    @NotNull
    private Boolean isDeviceAvailable;
    private LocalDateTime lastBookedOn;
    private Long lastBookedBy;
    private String technology;
    private String bands2G;
    private String bands3G;
    private String bands4G;
    private LocalDateTime createdAt;
    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
