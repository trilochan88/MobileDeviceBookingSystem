package com.ts.org.mobiledevicemangment.model.internal.db;

import com.ts.org.mobiledevicemangment.common.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@Entity
public class DeviceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceUserId;
    @NotBlank(message = "Employee Id is required")
    private String employeeId;
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotNull(message = "Role is required")
    private Role role;
    @Version
    private Long Version;
}
