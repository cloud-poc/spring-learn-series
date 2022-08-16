package org.akj.springboot.authorization.mapper;

import org.akj.springboot.authorization.domain.User;
import org.akj.springboot.authorization.vo.AddUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface UserMapstructMapper {
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "aliasName", target = "aliasName")
    @Mapping(source = "userRefId", target = "userRefId")
    User convert(AddUserRequest request);

}
