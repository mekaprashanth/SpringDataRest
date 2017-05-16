/**
 * 
 */
package com.prash.sdr.client;

/**
 * @author f2u85i8
 *
 */
public class ClientUser {
	
	private String userName;
	private String remoteIp;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((remoteIp == null) ? 0 : remoteIp.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientUser other = (ClientUser) obj;
		if (remoteIp == null) {
			if (other.remoteIp != null)
				return false;
		} else if (!remoteIp.equals(other.remoteIp))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "[userName=" + userName + ", remoteIp=" + remoteIp + "]";
	}
	
}
