package com.prash.sdr.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.prash.sdr.model.Client;
import com.prash.sdr.service.ClientService;

@Component("clientAuthenticationProvider")
public class ClientAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	ClientService clientService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		
		Client client = clientService.findByClientname(name);
		
		if(client == null)	{
			throw new AuthenticationServiceException("Given Client "+name+" not found in DB");
		}
		SimpleGrantedAuthority defaultAuthority = new SimpleGrantedAuthority("ADMIN");
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (password.equalsIgnoreCase(client.getClientsecret())) {
			authorities.add(defaultAuthority);
		} else {
			throw new InsufficientAuthenticationException("Bad Credentials");
		}
		return new UsernamePasswordAuthenticationToken(name, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}