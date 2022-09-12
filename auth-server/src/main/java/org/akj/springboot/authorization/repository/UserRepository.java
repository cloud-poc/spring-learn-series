package org.akj.springboot.authorization.repository;

import org.akj.springboot.authorization.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Search user by username
	 *
	 * @param userName
	 * @return
	 */
	User findByUserName(String userName);

}