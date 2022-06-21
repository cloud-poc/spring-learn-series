package org.akj.springboot.authorization.mapper;

import org.akj.springboot.authorization.domain.Group;
import org.akj.springboot.authorization.vo.GroupVo;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface GroupMapstructMapper {
    Group convert(GroupVo groupVo);

    GroupVo convert(Group group);

}
