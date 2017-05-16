/**
 * 
 */
package com.prash.sdr.service;

import com.prash.sdr.service.util.ServiceIdentifier;

/**
 * @author f2u85i8
 *
 */
public interface LocationServiceFactory {
	
	public LocationService getService(ServiceIdentifier si);

}
