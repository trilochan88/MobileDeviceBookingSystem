package com.ts.org.mobiledevicemangment.cqrs.queries.user;

import com.ts.org.mobiledevicemangment.common.mapping.DbModelToDtoMapper;
import com.ts.org.mobiledevicemangment.respository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceUserQueryServiceImpl implements DeviceUserQueryService {

    private final UserRepository userRepository;

    public DeviceUserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<DeviceUserDto> findAllUsers() {
        return userRepository.findAll().stream().map(DbModelToDtoMapper::toDto).toList();
    }

    @Override
    public Optional<DeviceUserDto> findUserById(Long userId) {
        return userRepository.findById(userId).map(DbModelToDtoMapper::toDto);
    }
}
