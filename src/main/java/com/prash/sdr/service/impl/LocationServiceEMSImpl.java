/**
 * 
 */
package com.prash.sdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prash.sdr.model.Location;
import com.prash.sdr.repository.LocationJpaRepository;

/**
 * @author f2u85i8
 *
 */

@Service("locationems")
public class LocationServiceEMSImpl extends LocationServiceImpl {

	@Autowired
	LocationJpaRepository locationRepository;
	
	@Override
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}

}
