package com.ts.org.mobiledevicemangment.respository;

import com.ts.org.mobiledevicemangment.model.internal.db.DeviceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DeviceUser, Long> {
}
