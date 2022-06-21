package org.akj.springboot.authorization.repository;

import org.akj.springboot.authorization.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	/**
	 * Search user by username
	 *
	 * @param userId
	 * @return
	 */
	User findByUserId(String userId);

}