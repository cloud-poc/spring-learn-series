package org.akj.springboot.authorization.service;

import io.micrometer.core.instrument.util.StringUtils;
import org.akj.springboot.authorization.domain.entity.User;
import org.akj.springboot.authorization.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User add(@NonNull User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }
}
