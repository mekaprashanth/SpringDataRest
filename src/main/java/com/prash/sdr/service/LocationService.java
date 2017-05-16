/**
 * 
 */
package com.prash.sdr.service;

import java.util.List;

import com.prash.sdr.model.Location;

/**
 * @author f2u85i8
 *
 */
public interface LocationService {
	
	public List<Location> getAllLocations();
	
	public Location saveLocation(Location location);

}
