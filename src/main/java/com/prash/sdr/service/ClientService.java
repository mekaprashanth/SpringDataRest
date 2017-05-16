/**
 * 
 */
package com.prash.sdr.service;

import com.prash.sdr.model.Client;

/**
 * @author f2u85i8
 *
 */
public interface ClientService {

	public Client findByClientname(String clientName);
}
