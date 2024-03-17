package com.ts.org.mobiledevicemangment.external;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FonoDeviceRequest {
    private String token;
    private int limit;
    private String modelName;
}
