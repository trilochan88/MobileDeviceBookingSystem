package com.ts.org.mobiledevicemangment.external;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FonoApiResponse {
    String modelName;
    String technology;
    String bands2g;
    String bands3g;
    String bands4g;
}
