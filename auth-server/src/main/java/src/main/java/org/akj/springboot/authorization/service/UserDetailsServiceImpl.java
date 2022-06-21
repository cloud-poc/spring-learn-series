package org.akj.springboot.authorization.service;

import java.util.Arrays;
import java.util.List;

import org.akj.springboot.authorization.entity.Users;
import org.akj.springboot.authorization.repository.UserDetailsRepository;
import org.akj.springboot.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        Users user = userDetailsRepository.findByUsername(userName);
        if (null == user) {
            throw new BusinessException("ERROR-010-001","User " + userName + " does not exists");
        }

        // List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (SysRole role : sysUser.getRoleList()) {
//            for (SysPermission permission : role.getPermissionList()) {
//                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
//            }
//        }
        return new User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("021098"),
                new SimpleGrantedAuthority("021040"));
    }
}
