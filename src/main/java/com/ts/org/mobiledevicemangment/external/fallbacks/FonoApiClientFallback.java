package com.ts.org.mobiledevicemangment.external.fallbacks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.org.mobiledevicemangment.external.FonoApiClient;
import com.ts.org.mobiledevicemangment.external.FonoApiResponse;
import com.ts.org.mobiledevicemangment.external.FonoDeviceRequest;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Component("fonoApiClientFallback")
public class FonoApiClientFallback implements FonoApiClient {
    @Override
    public Optional<FonoApiResponse> getDevice(FonoDeviceRequest deviceRequest) {
        try (InputStream inputStream = getClass().getResourceAsStream("/fallback-data/phones.json")) {
            ObjectMapper mapper = new ObjectMapper();
            List<FonoApiResponse> phones = mapper.readValue(inputStream, new TypeReference<>() {
            });
            return phones.stream().filter(phone -> phone.getModelName().equalsIgnoreCase(deviceRequest.getModelName())).findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}