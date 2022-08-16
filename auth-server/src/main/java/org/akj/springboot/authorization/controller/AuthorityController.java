package org.akj.springboot.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.authorization.domain.Authority;
import org.akj.springboot.authorization.mapper.AuthorityMapstructMapper;
import org.akj.springboot.authorization.repository.AuthorityRepository;
import org.akj.springboot.authorization.vo.AuthorityVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/authorities")
public class AuthorityController {
    private final AuthorityRepository authorityRepository;

    private final AuthorityMapstructMapper authorityMapstructMapper;

    public AuthorityController(AuthorityRepository authorityRepository,
                               AuthorityMapstructMapper authorityMapstructMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapstructMapper = authorityMapstructMapper;
    }

    @PutMapping
    @Operation(summary = "Add user authority")
    @PreAuthorize("hasAnyRole('admin')")
    public Authority addAuthority(@Valid @RequestBody AuthorityVo authorityVo) {
        var authority = authorityMapstructMapper.convert(authorityVo);
        return this.authorityRepository.save(authority);
    }

    @PostMapping("{authorityId}")
    @Operation(summary = "Update user authority")
    @PreAuthorize("hasAnyRole('admin') or hasAnyRole('editor')")
    public Authority updateAuthority(@PathVariable Integer authorityId, @Valid @RequestBody AuthorityVo authorityVo) {
        var authority = authorityMapstructMapper.convert(authorityVo);
        authority.setId(authorityId);
        return authorityRepository.save(authority);
    }

    @GetMapping
    @Operation(summary = "Retrieve user authorities and filter by authority name")
    @PreAuthorize("hasAnyRole('admin') or hasAnyRole('editor') or hasAnyRole('viewer')")
    public List<Authority> list(@RequestParam(value = "authorityName", required = false) String authorityName) {
        if (StringUtils.isNoneBlank(authorityName)) {
            return authorityRepository.findByNameLike(authorityName);
        }

        return authorityRepository.findAll();
    }

}
