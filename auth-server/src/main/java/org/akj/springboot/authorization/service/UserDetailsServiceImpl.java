package org.akj.springboot.authorization.service;

import org.akj.springboot.authorization.domain.Authority;
import org.akj.springboot.authorization.domain.ProfileStatus;
import org.akj.springboot.authorization.domain.User;
import org.akj.springboot.authorization.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = userDetailsRepository.findByUserName(userName);
        org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), new ArrayList<>());
        if (user != null) {
            var simpleGrantedAuthorities = user.getUserAuthorities()
                    .stream()
                    .map(Authority::getName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            userDetail = new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPassword(),
                    user.getStatus() == ProfileStatus.ACTIVE,
                    true,
                    true,
                    user.getStatus() != ProfileStatus.LOCKED,
                    simpleGrantedAuthorities);
        }

        return userDetail;
    }
}
