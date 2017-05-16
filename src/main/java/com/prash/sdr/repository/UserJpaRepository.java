/**
 * 
 */
package com.prash.sdr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prash.sdr.model.User;

/**
 * @author f2u85i8
 *
 */

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);

}
