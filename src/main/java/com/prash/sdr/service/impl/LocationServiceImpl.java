/**
 * 
 */
package com.prash.sdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prash.sdr.model.Location;
import com.prash.sdr.repository.LocationJpaRepository;
import com.prash.sdr.service.LocationService;

/**
 * @author f2u85i8
 *
 */

@Service("location")
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationJpaRepository locationRepository;
	
	@Override
	public List<Location> getAllLocations() {
		throw new UnsupportedOperationException("This operation is alliance specific but no alliance information is found");
	}

	@Override
	public Location saveLocation(Location location) {
		return locationRepository.save(location);
	}

}
