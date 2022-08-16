package org.akj.springboot.authorization.mapper;

import org.akj.springboot.authorization.domain.Authority;
import org.akj.springboot.authorization.vo.AuthorityVo;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface AuthorityMapstructMapper {
    Authority convert(AuthorityVo authorityVo);
}
