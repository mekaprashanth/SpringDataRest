/**
 * 
 */
package com.prash.sdr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prash.sdr.model.Client;

/**
 * @author f2u85i8
 *
 */

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, Long> {
	
	public Client findByClientname(String clientname);

}
