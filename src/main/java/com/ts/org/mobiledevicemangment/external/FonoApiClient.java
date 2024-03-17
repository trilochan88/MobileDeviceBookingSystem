package com.ts.org.mobiledevicemangment.external;

import com.ts.org.mobiledevicemangment.external.fallbacks.FonoApiClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "fonoapi", url = "https://fonoapi.freshpixl.com/v1",fallback = FonoApiClientFallback.class)

public interface FonoApiClient {
    @PostMapping(path = "/getdevice", consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<FonoApiResponse> getDevice(@RequestBody FonoDeviceRequest deviceRequest);
}

