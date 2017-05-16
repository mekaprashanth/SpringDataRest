/**
 * 
 */
package com.prash.sdr.client;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author f2u85i8
 *
 */
public class ClientAuthenticationDetails implements Serializable {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	// ~ Instance fields
	// ================================================================================================

	private final String remoteAddress;
	private final String sessionId;
	private final String userName;
	

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Records the remote address and will also set the session Id if a session already
	 * exists (it won't create one).
	 *
	 * @param request that the authentication request was received from
	 */
	public ClientAuthenticationDetails(HttpServletRequest request) {
		this.remoteAddress = request.getRemoteAddr();

		HttpSession session = request.getSession(false);
		this.sessionId = (session != null) ? session.getId() : null;
		
		this.userName = request.getHeader("x-username");
	}

	/**
	 * Constructor to add Jackson2 serialize/deserialize support
	 *
	 * @param remoteAddress remote address of current request
	 * @param sessionId session id
	 */
	@SuppressWarnings("unused")
	private ClientAuthenticationDetails(final String remoteAddress, final String sessionId, final String userName) {
		this.remoteAddress = remoteAddress;
		this.sessionId = sessionId;
		this.userName = userName;
	}

	// ~ Methods
	// ========================================================================================================

	public boolean equals(Object obj) {
		if (obj instanceof ClientAuthenticationDetails) {
			ClientAuthenticationDetails rhs = (ClientAuthenticationDetails) obj;

			if ((remoteAddress == null) && (rhs.getRemoteAddress() != null)) {
				return false;
			}

			if ((remoteAddress != null) && (rhs.getRemoteAddress() == null)) {
				return false;
			}

			if (remoteAddress != null) {
				if (!remoteAddress.equals(rhs.getRemoteAddress())) {
					return false;
				}
			}

			if ((sessionId == null) && (rhs.getSessionId() != null)) {
				return false;
			}

			if ((sessionId != null) && (rhs.getSessionId() == null)) {
				return false;
			}

			if (sessionId != null) {
				if (!sessionId.equals(rhs.getSessionId())) {
					return false;
				}
			}
			
			if ((userName == null) && (rhs.getUserName() != null)) {
				return false;
			}

			if ((userName != null) && (rhs.getUserName() == null)) {
				return false;
			}

			if (userName != null) {
				if (!userName.equals(rhs.getUserName())) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public String getUserName() {
		return userName;
	}

	/**
	 * Indicates the TCP/IP address the authentication request was received from.
	 *
	 * @return the address
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * Indicates the <code>HttpSession</code> id the authentication request was received
	 * from.
	 *
	 * @return the session ID
	 */
	public String getSessionId() {
		return sessionId;
	}

	public int hashCode() {
		int code = 7654;

		if (this.remoteAddress != null) {
			code = code * (this.remoteAddress.hashCode() % 7);
		}

		if (this.sessionId != null) {
			code = code * (this.sessionId.hashCode() % 7);
		}

		return code;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("RemoteIpAddress: ").append(this.getRemoteAddress()).append("; ");
		sb.append("SessionId: ").append(this.getSessionId());

		return sb.toString();
	}
}
