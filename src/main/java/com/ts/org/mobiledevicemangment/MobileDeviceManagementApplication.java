package com.ts.org.mobiledevicemangment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MobileDeviceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileDeviceManagementApplication.class, args);
    }

}
