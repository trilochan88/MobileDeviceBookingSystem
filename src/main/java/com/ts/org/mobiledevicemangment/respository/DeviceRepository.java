package com.ts.org.mobiledevicemangment.respository;

import com.ts.org.mobiledevicemangment.common.enums.Platform;
import com.ts.org.mobiledevicemangment.model.internal.db.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findAllByPlatform(Platform platform);
    List<Device> findAllByIsDeviceAvailableTrue();
}
