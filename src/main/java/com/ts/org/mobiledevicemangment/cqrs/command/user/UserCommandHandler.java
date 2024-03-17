package com.ts.org.mobiledevicemangment.cqrs.command.user;

import com.ts.org.mobiledevicemangment.common.mapping.DbModelToDtoMapper;
import com.ts.org.mobiledevicemangment.cqrs.queries.user.DeviceUserDto;
import com.ts.org.mobiledevicemangment.model.internal.db.DeviceUser;
import com.ts.org.mobiledevicemangment.respository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCommandHandler {
   private final UserRepository userRepository;

    public UserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public DeviceUserDto handleCreateUser(CreateUserCommand createUserCommand){
        DeviceUser deviceUser = new DeviceUser();
        deviceUser.setRole(createUserCommand.role());
        deviceUser.setName(createUserCommand.name());
        deviceUser.setEmail(createUserCommand.email());
        deviceUser.setEmployeeId(createUserCommand.employeeId());
        return DbModelToDtoMapper.toDto(userRepository.save(deviceUser));
    }

}
