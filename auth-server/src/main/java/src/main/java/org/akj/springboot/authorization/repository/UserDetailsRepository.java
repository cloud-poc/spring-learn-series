package org.akj.springboot.authorization.repository;

import org.akj.springboot.authorization.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<Users, Integer> {

	/**
	 * search user by user name
	 *
	 * @param userName
	 * @return
	 */
	Users findByUsername(String userName);

}