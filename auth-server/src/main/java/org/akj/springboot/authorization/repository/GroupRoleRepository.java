package org.akj.springboot.authorization.repository;

import org.akj.springboot.authorization.domain.entity.Group;
import org.akj.springboot.authorization.domain.entity.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRole, Long> {
}

