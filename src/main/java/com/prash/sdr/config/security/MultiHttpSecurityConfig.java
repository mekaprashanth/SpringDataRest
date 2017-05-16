package com.prash.sdr.config.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prash.sdr.client.ClientAuthenticationDetails;
import com.prash.sdr.spring.security.jwt.JwtAuthenticationTokenFilter;
import com.prash.sdr.spring.security.jwt.JwtLoginFilter;
import com.prash.sdr.spring.security.jwt.JwtTokenUtil;

@EnableWebSecurity()
public class MultiHttpSecurityConfig {

	@Configuration
	@Order(1)
	public static class CommonSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		AuthenticationManager authenticationManager;
		
		
		@Autowired
		private JwtTokenUtil jwtTokenUtil;

		@Value("${jwt.header}")
		private String tokenHeader;
		
		@Autowired
		@Qualifier("clientAuthenticationProvider")
		AuthenticationProvider authenticationProvider;
		
		@Autowired
		AuthenticationEntryPoint restEntryPoint;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.parentAuthenticationManager(authenticationManager)
			.authenticationProvider(authenticationProvider)
			;
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/**/api/**").authorizeRequests()
			.antMatchers("/**/api/date").permitAll()
			.antMatchers("/**/api/admin/**").hasAuthority("ADMIN")
			.antMatchers("/**/api/**").hasAnyAuthority("ADMIN","USER")
			.anyRequest().authenticated()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new JwtAuthenticationTokenFilter(jwtTokenUtil, tokenHeader), UsernamePasswordAuthenticationFilter.class)
			.httpBasic()
			.authenticationDetailsSource(httprequest->new ClientAuthenticationDetails(httprequest))
			.authenticationEntryPoint(restEntryPoint)
			;
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
	}

	@Configuration
	@Order(3)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private JwtTokenUtil jwtTokenUtil;

		@Value("${jwt.header}")
		private String tokenHeader;
		
		@Autowired
		@Qualifier("userservice")
		UserDetailsService userDetailsService;
		

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			final Filter loginFilter = new JwtLoginFilter(jwtTokenUtil, tokenHeader, "/login", "POST", authenticationManager());

			http.headers().frameOptions().sameOrigin().and().csrf().ignoringAntMatchers("/login")
			.and().authorizeRequests().antMatchers("/error").permitAll().anyRequest().authenticated()
			.and().formLogin().loginProcessingUrl("/login").failureUrl("/login?error").permitAll()
			.and().logout().permitAll()
			.and().addFilterBefore(loginFilter,UsernamePasswordAuthenticationFilter.class)
			.httpBasic().disable();
			
		}

		@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManager();
		}
	}
	
	
	@Configuration
	@Order(2)
	public static class BasicAuthWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Autowired
		@Qualifier("clientAuthenticationProvider")
		AuthenticationProvider authenticationProvider;
		
		@Autowired
		AuthenticationEntryPoint restEntryPoint;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider);
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/**/custom/**").authorizeRequests()
			.antMatchers("/**/custom/admin/**").hasAuthority("ADMIN")
			.antMatchers("/**/custom/**").hasAnyAuthority("ADMIN","USER")
			.anyRequest().authenticated()	
			.and().requiresChannel().anyRequest().requiresSecure()
			.and().httpBasic()
			.authenticationEntryPoint(restEntryPoint)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().csrf().disable();
		}
	}
}