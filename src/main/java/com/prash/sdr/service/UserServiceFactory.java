/**
 * 
 */
package com.prash.sdr.service;

import com.prash.sdr.service.util.ServiceTypeEnum;

/**
 * @author f2u85i8
 *
 */
public interface UserServiceFactory {
	
	public UserService getService(ServiceTypeEnum serviceType);

}
