/**
 * 
 */
package com.prash.sdr.service.util;

/**
 * @author f2u85i8
 *
 */
public enum ServiceTypeEnum {
	
	LOCATION("location", AllianceEnum.DEFAULT),
	MODEL("model", AllianceEnum.DEFAULT),
	USER("userservice", AllianceEnum.DEFAULT),
	CLIENT("clientservice", AllianceEnum.DEFAULT);
	
	private String serviceType;
	private AllianceEnum alliance;
	
	private ServiceTypeEnum(String serviceType, AllianceEnum alliance) {
		this.serviceType = serviceType;
		this.alliance = alliance;
	}
	
	public String serviceType()	{
		return this.serviceType;
	}
	
	@Override
	public String toString()	{
		return this.serviceType+this.alliance;
	}

}
