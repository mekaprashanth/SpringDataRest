/**
 * 
 */
package com.prash.sdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prash.sdr.model.Location;
import com.prash.sdr.repository.LocationRepository;

/**
 * @author f2u85i8
 *
 */

@Service("locationfdp")
public class LocationServiceFDPImpl extends LocationServiceImpl {

	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}

}
