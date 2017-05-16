/**
 * 
 */
package com.prash.sdr.service.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author f2u85i8
 *
 */
public enum AllianceEnum {
	DEFAULT(""),
	EMS("ems"),
	FDP("fdp"),
	BANCO("bnc");
	
	private static Map<String, AllianceEnum> map = new HashMap<>();
	
	static	{
		map = Arrays.stream(AllianceEnum.values()).collect(Collectors.toMap(k->k.getAllianceName(), v->v));
	}
	
	private String allianceName;
	
	private AllianceEnum(String allianceName) {
		this.allianceName = allianceName;
	}
	
	public String getAllianceName()	{
		return this.allianceName;
	}
	
	public static AllianceEnum getAllianceFromCode(String allianceCode)	{
		allianceCode = (allianceCode == null)?"":allianceCode;
		AllianceEnum ae = map.get(allianceCode);
		if(ae == null)	{
			throw new IllegalArgumentException("Invalid Alliance Code "+allianceCode);
		}
		return ae;
	}
	
	@Override
	public String toString()	{
		return this.allianceName;
	}

}
