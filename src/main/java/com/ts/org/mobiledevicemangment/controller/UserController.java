package com.ts.org.mobiledevicemangment.controller;

import com.ts.org.mobiledevicemangment.cqrs.command.user.CreateUserCommand;
import com.ts.org.mobiledevicemangment.cqrs.command.user.UserCommandHandler;
import com.ts.org.mobiledevicemangment.cqrs.queries.user.DeviceUserDto;
import com.ts.org.mobiledevicemangment.cqrs.queries.user.DeviceUserQueryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private final DeviceUserQueryService deviceUserQueryService;
    private final UserCommandHandler userCommandHandler;

    public UserController(DeviceUserQueryService deviceUserQueryService, UserCommandHandler userCommandHandler) {
        this.deviceUserQueryService = deviceUserQueryService;
        this.userCommandHandler = userCommandHandler;
    }

    @PostMapping
    public ResponseEntity<DeviceUserDto> createUser(@Valid @RequestBody CreateUserCommand createUserCommand) {
        DeviceUserDto newUser = userCommandHandler.handleCreateUser(createUserCommand);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DeviceUserDto> getUserById(@PathVariable Long userId) {
        return deviceUserQueryService.findUserById(userId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DeviceUserDto>> getAllUsers() {
        return ResponseEntity.ok(deviceUserQueryService.findAllUsers());
    }
}
