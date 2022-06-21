package org.akj.springboot.authorization.repository;

import org.akj.springboot.authorization.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    List<Authority> findByNameLike(String authorityName);

    Authority findByName(String authorityName);

    List<Authority> findByNameIn(List<String> authorityNames);
}

