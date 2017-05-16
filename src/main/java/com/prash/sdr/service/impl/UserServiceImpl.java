/**
 * 
 */
package com.prash.sdr.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prash.sdr.model.User;
import com.prash.sdr.repository.UserJpaRepository;
import com.prash.sdr.service.UserService;

/**
 * @author f2u85i8
 *
 */

@Service("userservice")
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserJpaRepository userRepository;

	@Override
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null)	{
			throw new UsernameNotFoundException(username+" is not found in DB");
		}
		UserDetails userdetails = new UserDetails() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				return user.isEnabled();
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return user.getUsername();
			}
			
			@Override
			public String getPassword() {
				return user.getPassword();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return user.getRoles().stream().map(p -> new SimpleGrantedAuthority(p.getAuthority())).collect(Collectors.toList());
			}
		};
		return userdetails;
	}

}
