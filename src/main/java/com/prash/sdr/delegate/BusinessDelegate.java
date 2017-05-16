/**
 * 
 */
package com.prash.sdr.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prash.sdr.model.Location;
import com.prash.sdr.service.LocationService;
import com.prash.sdr.service.LocationServiceFactory;
import com.prash.sdr.service.ModelServiceFactory;
import com.prash.sdr.service.util.AllianceEnum;
import com.prash.sdr.service.util.ServiceIdentifier;
import com.prash.sdr.service.util.ServiceTypeEnum;

/**
 * @author f2u85i8
 *
 */

@Component
public class BusinessDelegate {
	@Autowired
	LocationServiceFactory locationServiceFactory;
	
	@Autowired
	ModelServiceFactory modelServiceFactory;
	
	
	public List<Location> getAllLocations(String allianceCode)	{
		AllianceEnum alliance = AllianceEnum.getAllianceFromCode(allianceCode);
		LocationService service = locationServiceFactory.getService(ServiceIdentifier.getServiceIdentifier(ServiceTypeEnum.LOCATION, alliance));
		return service.getAllLocations();
	}
	
	public Location saveLocation(Location location)	{
		LocationService service = locationServiceFactory.getService(ServiceIdentifier.createServiceIdentifier(ServiceTypeEnum.LOCATION));
		return service.saveLocation(location);
	}
}
