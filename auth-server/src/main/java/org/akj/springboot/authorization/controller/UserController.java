package org.akj.springboot.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.authorization.domain.Authority;
import org.akj.springboot.authorization.domain.ProfileStatus;
import org.akj.springboot.authorization.domain.User;
import org.akj.springboot.authorization.mapper.UserMapstructMapper;
import org.akj.springboot.authorization.repository.AuthorityRepository;
import org.akj.springboot.authorization.repository.UserRepository;
import org.akj.springboot.authorization.vo.AddUserRequest;
import org.akj.springboot.authorization.vo.GrantUserAuthorityRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapstructMapper userMapstructMapper;

    public UserController(UserRepository userRepository,
                          AuthorityRepository authorityRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserMapstructMapper userMapstructMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapstructMapper = userMapstructMapper;
    }

    @PutMapping
    @Operation(summary = "User registration.")
    @PreAuthorize("hasAnyRole('admin')")
    public User register(@Valid @RequestBody AddUserRequest request) {
        User user = userMapstructMapper.convert(request);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(ProfileStatus.ACTIVE);
        return userRepository.save(user);
    }

    @PostMapping("{userId}/groups")
    @Operation(summary = "Add user to group")
    @PreAuthorize("hasAnyRole('admin') or hasAnyRole('editor')")
    public ResponseEntity addToGroup(@PathVariable("userId") Integer userId, @RequestBody @Valid GrantUserAuthorityRequest request) {

        List<String> authorities = request.getAuthorities();
        List<Authority> authorityList = authorityRepository.findByNameIn(authorities);
        if (null != authorityList && authorityList.size() != authorities.size()) {
            log.warn("Some user authorities received are invalid, will ignore these invalid authorities.");
        }
        userRepository.findById(userId).ifPresent(u -> {
            u.getUserAuthorities().addAll(authorityList);
            userRepository.save(u);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("{userId}")
    @PreAuthorize("hasAnyRole('admin') or hasAnyRole('editor') or hasAnyRole('viewer')")
    public User getUserDetails(@PathVariable("userId") Integer userId){
        return userRepository.findById(userId).orElse(null);
    }

}
