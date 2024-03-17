package com.ts.org.mobiledevicemangment.cqrs.command.user;

import com.ts.org.mobiledevicemangment.common.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserCommand(
        @NotBlank(message = "Employee ID must not be empty") String employeeId,
        @NotBlank(message = "Name must not be empty") String name,
        @Email(message = "Email must be a valid email address")
        @NotBlank(message = "Email must not be empty")
        String email,
        @NotNull(message = "Role must not be null") Role role) {

}
