/**
 * 
 */
package com.prash.sdr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prash.sdr.model.Client;
import com.prash.sdr.repository.ClientJpaRepository;
import com.prash.sdr.service.ClientService;

/**
 * @author f2u85i8
 *
 */

@Service("clientservice")
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	ClientJpaRepository clientRepository;

	@Override
	public Client findByClientname(String clientName) {
		return clientRepository.findByClientname(clientName);
	}

	

}
