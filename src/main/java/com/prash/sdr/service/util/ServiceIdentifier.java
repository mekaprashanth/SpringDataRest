/**
 * 
 */
package com.prash.sdr.service.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author f2u85i8
 *
 */
public class ServiceIdentifier {
	
	private static Map<String, ServiceIdentifier> map = new HashMap<>(); 

	public static ServiceIdentifier createServiceIdentifier(ServiceTypeEnum serviceType) {
		return getServiceIdentifier(serviceType, AllianceEnum.DEFAULT);
	}

	public static ServiceIdentifier getServiceIdentifier(ServiceTypeEnum serviceType, AllianceEnum alliance) {
		String key = serviceType+alliance.toString();
		ServiceIdentifier si = map.get(key);
		if(si == null)	{
			si = new ServiceIdentifier(serviceType, alliance);
			map.put(key, si);
		}
		return si;
	}

	private ServiceTypeEnum serviceType;
	private AllianceEnum alliance;

	private ServiceIdentifier(ServiceTypeEnum serviceType, AllianceEnum alliance) {
		this.serviceType = serviceType;
		this.alliance = alliance;
	}

	@Override
	public String toString() {
		return serviceType + alliance.toString();
	}
}
